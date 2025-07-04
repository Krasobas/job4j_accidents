package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccidentRepository implements AccidentRepository {
    private final Map<Long, Accident> store = new ConcurrentHashMap<>();

    @Override
    public Accident save(Accident accident) {
        Accident existing = store.putIfAbsent(accident.getId(), accident);
        return Objects.isNull(existing) ? accident : existing;
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
        return store.values().stream().filter(entity -> entity.getName().equals(name)).toList();
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
}




