package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.mapstruct.AccidentMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.repository.type.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository repository;
    private final AccidentMapper mapper;
    private final AccidentTypeRepository typeRepository;

    @Override
    public Optional<AccidentDto> save(AccidentCreateDto accident) {
        Optional<AccidentType> type = typeRepository.findById(accident.getTypeId());
        if (type.isEmpty()) return Optional.empty();
        Accident entity = mapper.getEntity(accident, type.get());
        entity = repository.save(entity);
        return Optional.ofNullable(mapper.getDto(entity));
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
    public boolean update(AccidentEditDto accident) {
        Optional<AccidentType> type = typeRepository.findById(accident.getTypeId());
        return type.isPresent() && repository.update(mapper.getEntity(accident, type.get()));
    }
}
