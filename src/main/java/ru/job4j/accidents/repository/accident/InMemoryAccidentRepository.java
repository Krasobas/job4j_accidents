package ru.job4j.accidents.repository.accident;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.job4j.accidents.utils.Utils.generateUniqueId;

@Profile("ram")
@Repository
public class InMemoryAccidentRepository implements AccidentRepository {
    private final Map<Long, Accident> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Long> save(Accident accident) {
        accident.setId(generateUniqueId());
        Accident existing = store.putIfAbsent(accident.getId(), accident);
        return Objects.isNull(existing) ? Optional.of(accident.getId()) : Optional.empty();
    }

    @Override
    public Optional<Accident> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return store.values();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public Collection<Accident> findByName(String name) {
        return store.values().stream().filter(entity -> {
            String current = entity.getName();
            return Objects.nonNull(current) && current.toLowerCase().contains(name);
        }).toList();
    }

    @Override
    public Collection<Accident> findByAddress(String address) {
        return store.values().stream().filter(entity -> entity.getAddress().equals(address)).toList();
    }

    @Override
    public Collection<Accident> findByTextPhrase(String phrase) {
        return store.values()
                .stream()
                .filter(entity -> {
                    String text = entity.getText();
                    return Objects.nonNull(text) && text.toLowerCase().contains(phrase);
                })
                .toList();
    }

    @Override
    public boolean update(Accident accident) {
        return Objects.nonNull(store.replace(accident.getId(), accident));
    }
}




