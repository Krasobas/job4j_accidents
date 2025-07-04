package ru.job4j.accidents.repository.accident;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {
    Accident save(Accident accident);

    Optional<Accident> findById(Long id);

    Collection<Accident> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Collection<Accident> findByName(String name);

    Collection<Accident> findByAddress(String address);

    Collection<Accident> findByTextPhrase(String phrase);

    boolean update(Accident accident);
}
