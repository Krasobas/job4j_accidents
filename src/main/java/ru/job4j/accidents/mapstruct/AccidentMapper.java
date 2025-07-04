package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

@Mapper(componentModel = "spring")
public interface AccidentMapper {

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    Accident getEntity(AccidentEditDto dto, AccidentType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.name", target = "name")
    Accident getEntity(AccidentCreateDto dto, AccidentType type);

    @Mapping(source = "type.id", target = "typeId")
    AccidentEditDto getEditDto(Accident entity);

    @Mapping(source = "type.name", target = "type")
    AccidentDto getDto(Accident entity);
}
