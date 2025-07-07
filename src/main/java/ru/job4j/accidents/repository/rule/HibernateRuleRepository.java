package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Profile("hibernate")
@Repository
@AllArgsConstructor
public class HibernateRuleRepository implements RuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAll() {
        try {
            return crudRepository.query(
                    """
                        from Rule r
                        order by r.name asc
                        """,
                    Rule.class
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Rule> findAll(Collection<Long> ids) {
        try {
            return crudRepository.query(
                    """
                        from Rule r
                        where r.id in :fIds
                        order by r.name asc
                        """,
                    Rule.class,
                    Map.of("fIds",  ids)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Rule> findById(Long id) {
        try {
            return crudRepository.optional(
                    """
                        from Rule r
                        where r.id = :fId
                        order by r.name asc
                        """,
                    Rule.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
