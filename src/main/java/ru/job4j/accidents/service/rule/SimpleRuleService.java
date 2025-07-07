package ru.job4j.accidents.service.rule;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.rule.RuleDto;
import ru.job4j.accidents.mapstruct.RuleMapper;
import ru.job4j.accidents.repository.rule.RuleRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {
    private final RuleRepository repository;
    private final RuleMapper mapper;

    @Override
    public Collection<RuleDto> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::getDto).toList();
    }

    @Override
    public Optional<RuleDto> findById(Long id) {
        return repository.findById(id).map(mapper::getDto);
    }
}
