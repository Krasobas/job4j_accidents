package ru.job4j.accidents.repository.rule;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Profile("ram")
@Repository
public class InMemoryRuleRepository implements RuleRepository {
    private final Map<Long, Rule> store = new ConcurrentHashMap<>(
            LongStream.range(1L, 10L)
                    .boxed()
                    .collect(Collectors.toMap(
                            id -> id,
                            id -> new Rule(id, String.format("Article %d.0", id))
                    ))
    );

    @Override
    public Collection<Rule> findAll() {
        return store.values();
    }

    @Override
    public Collection<Rule> findAll(Collection<Long> ids) {
        return store.values().stream().filter(rule -> ids.contains(rule.getId())).toList();
    }

    @Override
    public Optional<Rule> findById(Long id) {
        return Optional.ofNullable(store.get(id));

    }
}
