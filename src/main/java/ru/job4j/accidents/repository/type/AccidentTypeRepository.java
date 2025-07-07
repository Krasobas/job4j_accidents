package ru.job4j.accidents.repository.type;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public interface  AccidentTypeRepository extends CrudRepository<AccidentType, Long> {
}
