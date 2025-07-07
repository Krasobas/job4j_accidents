package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.CrudRepository;
import java.util.*;

@Log4j2
@Profile("hibernate")
@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Long> save(Accident accident) {
        try {
            Optional<Accident> created = crudRepository.runFunction(session -> session.merge(accident));
            return created.map(Accident::getId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Accident> findById(Long id) {
        try {
            return crudRepository.optional(
                    """
                    select distinct a from Accident a
                    join fetch a.type
                    left join fetch a.rules
                    where a.id = :fId
                    """,
                    Accident.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        try {
            return crudRepository.query(
                    """
                        select distinct a from Accident a
                        join fetch a.type
                        left join fetch a.rules
                        order by a.id asc, a.name asc
                        """,
                    Accident.class
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        try {
            crudRepository.query(
                    "delete from Accident where id = :fId",
                    Accident.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return crudRepository.optional(
                    """
                    select name from Accident
                    where a.id = :fId
                    """,
                    Accident.class,
                    Map.of("fId", id)
            ).isPresent();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Collection<Accident> findByName(String name) {
        try {
            return crudRepository.query(
                    """
                        select distinct a from Accident a
                        join fetch a.type
                        left join fetch a.rules
                        where a.name ilike :fName
                        order by a.id asc, a.name asc
                        """,
                    Accident.class,
                    Map.of("fName", String.format("%%%s%%", name))
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Accident> findByAddress(String address) {
        try {
            return crudRepository.query(
                    """
                        select distinct a from Accident a
                        join fetch a.type
                        left join fetch a.rules
                        where a.address = :fAddress
                        order by a.id asc, a.name asc
                        """,
                    Accident.class,
                    Map.of("fAddress", address)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Accident> findByTextPhrase(String phrase) {
        try {
            return crudRepository.query(
                    """
                        select distinct a from Accident a
                        join fetch a.type
                        left join fetch a.rules
                        where a.text ilike :fText
                        order by a.id asc, a.name asc
                        """,
                    Accident.class,
                    Map.of("fText", String.format("%%%s%%", phrase))
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Accident accident) {
        try {
            return findById(accident.getId())
                    .map(origin -> {
                        origin.setType(accident.getType());
                        origin.setName(accident.getName());
                        origin.setText(accident.getText());
                        origin.setAddress(accident.getAddress());
                        origin.setRules(accident.getRules());
                        crudRepository.run(session -> session.merge(origin));
                        return origin;
                    }).isPresent();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
