package ru.job4j.accidents.service.accident;

import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {
    Optional<AccidentDto> save(AccidentCreateDto accident, Set<Long> ruleIds);

    Optional<AccidentDto> findById(Long id);

    Optional<AccidentEditDto> findByIdOnEdit(Long id);

    List<AccidentDto> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<AccidentDto> findByName(String name);

    List<AccidentDto> findByAddress(String address);

    List<AccidentDto> findByTextPhrase(String phrase);

    boolean update(AccidentEditDto accident, Set<Long> ruleIds);
}
