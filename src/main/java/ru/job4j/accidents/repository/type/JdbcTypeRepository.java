package ru.job4j.accidents.repository.type;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleRepository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Profile("jdbc")
@Repository
@AllArgsConstructor
public class JdbcTypeRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query(
                "select id, name from accident_type",
                (rs, row) -> new AccidentType(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Optional<AccidentType> findById(Long id) {
        AccidentType found = jdbc.queryForObject(
                "select id, name from accident_type where id = ?",
                (rs, row) -> new AccidentType(
                            rs.getLong("id"),
                            rs.getString("name")
                    ),
                id);
        return Optional.ofNullable(found);
    }
}
