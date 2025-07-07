package ru.job4j.accidents.repository.accident;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentRepository extends CrudRepository<Accident, Long> {

    List<Accident> findByNameContainingIgnoreCase(String name);

    List<Accident> findByAddress(String address);

    List<Accident> findByTextContainingIgnoreCase(String text);

}
