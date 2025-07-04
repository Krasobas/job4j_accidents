package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.AccidentCreateDto;
import ru.job4j.accidents.dto.AccidentDto;
import ru.job4j.accidents.mapstruct.AccidentMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository repository;
    private final AccidentMapper mapper;

    @Override
    public AccidentDto save(AccidentCreateDto accident) {
        Accident entity = mapper.getEntity(accident);
        entity = repository.save(entity);
        return mapper.getDto(entity);
    }

    @Override
    public Optional<AccidentDto> findById(Long id) {
        return repository.findById(id).map(mapper::getDto);
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
}
