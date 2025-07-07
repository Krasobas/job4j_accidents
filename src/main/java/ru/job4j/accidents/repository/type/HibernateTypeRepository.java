package ru.job4j.accidents.repository.type;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Profile("hibernate")
@Repository
@AllArgsConstructor
public class HibernateTypeRepository implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<AccidentType> findAll() {
        try {
            return crudRepository.query(
                    """
                        from AccidentType at
                        order by at.name asc
                        """,
                    AccidentType.class
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<AccidentType> findById(Long id) {
        try {
            return crudRepository.optional(
                    """
                        from AccidentType at
                        where at.id = :fId
                        order by at.name asc
                        """,
                    AccidentType.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
