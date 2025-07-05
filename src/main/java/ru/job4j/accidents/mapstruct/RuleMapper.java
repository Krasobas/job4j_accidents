package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import ru.job4j.accidents.dto.rule.RuleDto;
import ru.job4j.accidents.model.Rule;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    Rule getEntity(RuleDto dto);

    RuleDto getDto(Rule entity);
}
