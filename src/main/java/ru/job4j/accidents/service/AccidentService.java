package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.AccidentCreateDto;
import ru.job4j.accidents.dto.AccidentDto;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    AccidentDto save(AccidentCreateDto accident);

    Optional<AccidentDto> findById(Long id);

    List<AccidentDto> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<AccidentDto> findByName(String name);

    List<AccidentDto> findByAddress(String address);

    List<AccidentDto> findByTextPhrase(String phrase);
}
