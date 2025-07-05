package ru.job4j.accidents.service.rule;

import ru.job4j.accidents.dto.rule.RuleDto;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {
    Collection<RuleDto> findAll();

    Optional<RuleDto> findById(Long id);
}
