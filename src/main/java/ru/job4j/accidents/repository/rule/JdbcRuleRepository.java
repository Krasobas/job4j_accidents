package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Profile("jdbc")
@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query(
                "select id, name from rule",
                (rs, row) -> new Rule(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Collection<Rule> findAll(Collection<Long> ids) {
        String sql = String.format(
                "select id, name from rule where id in (%s)",
                ids.stream().map(id -> "?").collect(Collectors.joining(","))
                );
        return jdbc.query(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    int index = 1;
                    for (Long id : ids) {
                        ps.setLong(index++, id);
                    }
                    return ps;
                },
                (rs, row) -> new Rule(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Optional<Rule> findById(Long id) {
        Rule found = jdbc.queryForObject(
                "select id, name from rule where id = ?",
                (rs, row) -> new Rule(
                            rs.getLong("id"),
                            rs.getString("name")
                    ),
                id);
        return Optional.ofNullable(found);
    }
}
