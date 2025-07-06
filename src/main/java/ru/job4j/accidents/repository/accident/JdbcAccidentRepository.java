package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Log4j2
@Profile("jdbc")
@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Long> save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String accidentSql = """
                insert into accident (name, text, address, type_id)
                values (?, ?, ?, ?) returning id
                """;

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    accidentSql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setLong(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (Objects.isNull(key)) {
            return Optional.empty();
        }
        Long id = key.longValue();
        accident.setId(id);
        Collection<Rule> rules = accident.getRules();
        if (Objects.nonNull(rules)) {
            for (Rule rule : rules) {
                jdbc.update(
                        "insert into accident_rule (accident_id, rule_id) values (?, ?)",
                        id, rule.getId()
                );
            }
        }
        return Optional.of(id);
    }

    @Override
    public Optional<Accident> findById(Long id) {
        String accidentSql = """
                select a.id, a.name, a.text, a.address,
                       t.id as type_id, t.name as type_name
                from accident a
                join accident_type t on a.type_id = t.id
                where a.id = ?
                """;
        try {
            Accident accident = jdbc.queryForObject(
                    accidentSql,
                    (rs, rowNum) -> {
                        Accident found = new Accident();
                        found.setId(rs.getLong("id"));
                        found.setName(rs.getString("name"));
                        found.setText(rs.getString("text"));
                        found.setAddress(rs.getString("address"));

                        AccidentType type = new AccidentType();
                        type.setId(rs.getLong("type_id"));
                        type.setName(rs.getString("type_name"));
                        found.setType(type);

                        return found;
                    },
                    id
            );
            if (Objects.isNull(accident)) {
                return Optional.empty();
            }

            Collection<Rule> rules = findRulesByAccidentId(id);

            accident.setRules(new HashSet<>(rules));
            return Optional.of(accident);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        String sql = """
                select a.id, a.name, a.text, a.address,
                       t.id as typeId, t.name as typeName,
                       r.id as ruleId, r.name as ruleName
                from accident a
                join accident_type t on a.type_id = t.id
                left join accident_rule ar on a.id = ar.accident_id
                left join rule r on ar.rule_id = r.id
                order by a.id
                """;
        Map<Long, Accident> accidents = new HashMap<>();
        jdbc.query(sql, rs -> {
            Long id = rs.getLong("id");
            Accident accident = accidents.get(id);
            if (Objects.isNull(accident)) {
                accident = new Accident();
                accident.setId(rs.getLong("id"));
                accident.setName(rs.getString("name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));
                AccidentType type = new AccidentType(
                        rs.getLong("typeId"),
                        rs.getString("typeName")
                );
                accident.setType(type);
                accident.setRules(new HashSet<>());
                accidents.put(id, accident);
            }
            Object ruleId = rs.getObject("ruleId");
            if (Objects.nonNull(ruleId)) {
                Rule rule = new Rule(
                        rs.getLong("ruleId"),
                        rs.getString("ruleName")
                );
                accident.getRules().add(rule);
            }
        });
        return accidents.values();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Collection<Accident> findByName(String name) {
        return List.of();
    }

    @Override
    public Collection<Accident> findByAddress(String address) {
        return List.of();
    }

    @Override
    public Collection<Accident> findByTextPhrase(String phrase) {
        return List.of();
    }

    @Override
    public boolean update(Accident accident) {
        String sql = """
                update accident
                set name = ?, text = ?, address = ?, type_id = ?
                where id = ?
                """;
        long id = accident.getId();
        int updated = jdbc.update(
                sql,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                id
        );

        if (updated == 1) {
            updateRules(id, accident.getRules());
            return true;
        }
        return false;
    }

    private Collection<Rule> findRulesByAccidentId(Long id) {
        String rulesSql = """
                select r.id, r.name
                from rule r
                join accident_rule ar on r.id = ar.rule_id
                where ar.accident_id = ?
                """;
        return jdbc.query(
                rulesSql,
                (rs, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getLong("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id
        );
    }

    private void updateRules(Long id, Collection<Rule> newRules) {
        Set<Rule> currentRules = new HashSet<>(findRulesByAccidentId(id));

        Set<Rule> toRemove = new HashSet<>(currentRules);
        toRemove.removeAll(newRules);
        if (!toRemove.isEmpty()) {
            jdbc.batchUpdate(
                    "delete from accident_rule where accident_id = ? and rule_id = ?",
                    toRemove.stream()
                            .map(rule -> new Object[]{id, rule.getId()})
                            .toList()
            );
        }
        Set<Rule> toAdd = new HashSet<>(newRules);
        toAdd.removeAll(currentRules);
        if (!toAdd.isEmpty()) {
            jdbc.batchUpdate(
                    "insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    toAdd.stream()
                            .map(rule -> new Object[] {id, rule.getId()})
                            .toList()
            );
        }
    }
}
