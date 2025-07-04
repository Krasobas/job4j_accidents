package ru.job4j.accidents.repository.type;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccidentTypeRepository implements AccidentTypeRepository {
    private final Map<Long, AccidentType> store = new ConcurrentHashMap<>(
            Map.of(
                    1L, new AccidentType(1L, "Two cars"),
                    2L, new AccidentType(2L, "Car and person"),
                    3L, new AccidentType(3L, "Car and bicycle")
            )
    );

    @Override
    public Collection<AccidentType> findAll() {
        return store.values();
    }

    @Override
    public Optional<AccidentType> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
