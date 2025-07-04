package ru.job4j.accidents.dto.accident;

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
    private Long typeId;
    private String text;
    private String address;
}