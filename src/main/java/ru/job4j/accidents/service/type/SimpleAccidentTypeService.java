package ru.job4j.accidents.service.type;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.type.AccidentTypeDto;
import ru.job4j.accidents.mapstruct.AccidentTypeMapper;
import ru.job4j.accidents.repository.type.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeRepository repository;
    private final AccidentTypeMapper mapper;

    @Override
    public Collection<AccidentTypeDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::getDto)
                .toList();
    }

    @Override
    public Optional<AccidentTypeDto> findById(Long id) {
        return repository.findById(id).map(mapper::getDto);
    }
}
