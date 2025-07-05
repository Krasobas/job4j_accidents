package ru.job4j.accidents.repository.rule;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {
    Collection<Rule> findAll();

    Collection<Rule> findAll(Collection<Long> ids);

    Optional<Rule> findById(Long id);
}
