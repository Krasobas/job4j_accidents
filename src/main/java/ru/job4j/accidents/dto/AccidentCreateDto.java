package ru.job4j.accidents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentCreateDto {
    @EqualsAndHashCode.Include
    private String name;
    private String text;
    private String address;
}