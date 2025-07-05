package ru.job4j.accidents.dto.accident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String type;
    private String name;
    private String text;
    private String address;
    private Set<String> rules;
}