package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.accidents.dto.AccidentCreateDto;
import ru.job4j.accidents.dto.AccidentDto;
import ru.job4j.accidents.model.Accident;

@Mapper(componentModel = "spring")
public interface AccidentMapper {

    Accident getEntity(AccidentDto dto);

    @Mapping(target = "id", ignore = true)
    Accident getEntity(AccidentCreateDto dto);

    AccidentDto getDto(Accident entity);
}
