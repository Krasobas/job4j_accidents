package ru.job4j.accidents.dto.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentTypeDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
}
