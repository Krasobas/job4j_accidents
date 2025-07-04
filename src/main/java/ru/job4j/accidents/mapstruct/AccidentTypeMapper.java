package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import ru.job4j.accidents.dto.type.AccidentTypeDto;
import ru.job4j.accidents.model.AccidentType;

@Mapper(componentModel = "spring")
public interface AccidentTypeMapper {

    AccidentType getEntity(AccidentTypeDto dto);

    AccidentTypeDto getDto(AccidentType entity);
}
