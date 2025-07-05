package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccidentMapper {

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    Accident getEntity(AccidentEditDto dto, AccidentType type, Set<Rule> rules);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.name", target = "name")
    Accident getEntity(AccidentCreateDto dto, AccidentType type, Set<Rule> rules);

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "entity.rules", target = "ruleIds")
    AccidentEditDto getEditDto(Accident entity);

    @Mapping(source = "type.name", target = "type")
    AccidentDto getDto(Accident entity);

    default Set<String> rulesToRuleNames(Set<Rule> rules) {
        return Objects.isNull(rules) ? Collections.emptySet() : rules.stream().map(Rule::getName).collect(Collectors.toSet());
    }

    default Set<Long> rulesToRuleIds(Set<Rule> rules) {
        return Objects.isNull(rules) ? Collections.emptySet() : rules.stream().map(Rule::getId).collect(Collectors.toSet());
    }
}
