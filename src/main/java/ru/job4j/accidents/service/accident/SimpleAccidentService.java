package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.mapstruct.AccidentMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.repository.rule.RuleRepository;
import ru.job4j.accidents.repository.type.AccidentTypeRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository repository;
    private final AccidentMapper mapper;
    private final AccidentTypeRepository typeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Optional<Long> save(AccidentCreateDto accident, Set<Long> ruleIds) {
        Optional<AccidentType> type = typeRepository.findById(accident.getTypeId());
        if (type.isEmpty()) {
            return Optional.empty();
        }
        Set<Rule> rules = StreamSupport.stream(
                ruleRepository.findAllById(ruleIds).spliterator(),
                        false)
                .collect(Collectors.toSet());
        Accident entity = mapper.getEntity(accident, type.get(), rules);
        return Optional.ofNullable(repository.save(entity)).map(Accident::getId);
    }

    @Override
    public Optional<AccidentDto> findById(Long id) {
        return repository.findById(id).map(mapper::getDto);
    }

    @Override
    public Optional<AccidentEditDto> findByIdOnEdit(Long id) {
        return repository.findById(id).map(mapper::getEditDto);
    }

    @Override
    public List<AccidentDto> findAll() {
        return StreamSupport.stream(
                        repository.findAll().spliterator(),
                        false
                )
                .map(mapper::getDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<AccidentDto> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::getDto)
                .toList();
    }

    @Override
    public List<AccidentDto> findByAddress(String address) {
        return repository.findByAddress(address)
                .stream()
                .map(mapper::getDto)
                .toList();
    }

    @Override
    public List<AccidentDto> findByTextPhrase(String phrase) {
        return repository.findByTextContainingIgnoreCase(phrase)
                .stream()
                .map(mapper::getDto)
                .toList();
    }

    @Override
    public boolean update(AccidentEditDto accident, Set<Long> ruleIds) {
        Optional<AccidentType> type = typeRepository.findById(accident.getTypeId());
        if (type.isEmpty()) {
            return false;
        }
        Set<Rule> rules = StreamSupport.stream(ruleRepository.findAllById(ruleIds).spliterator(), false).collect(Collectors.toSet());
        return type.isPresent() && Objects.nonNull(repository.save(mapper.getEntity(accident, type.get(), rules)));
    }
}
