package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.Set;

import static ru.job4j.accidents.utils.Utils.generateUniqueId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    @EqualsAndHashCode.Include
    private Long id = generateUniqueId();
    private AccidentType type;
    private String name;
    private String text;
    private String address;
    private Set<Rule> rules;
}