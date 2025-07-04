package ru.job4j.accidents.service.type;

import ru.job4j.accidents.dto.type.AccidentTypeDto;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {
    Collection<AccidentTypeDto> findAll();

    Optional<AccidentTypeDto> findById(Long id);
}
