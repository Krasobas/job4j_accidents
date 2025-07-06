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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Set<Rule> rules = new HashSet<>(ruleRepository.findAll(ruleIds));
        Accident entity = mapper.getEntity(accident, type.get(), rules);
        return repository.save(entity);
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
        return repository.findAll()
                .stream()
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
        return repository.findByName(name)
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
        return repository.findByTextPhrase(phrase)
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
        Set<Rule> rules = new HashSet<>(ruleRepository.findAll(ruleIds));
        return type.isPresent() && repository.update(mapper.getEntity(accident, type.get(), rules));
    }
}
