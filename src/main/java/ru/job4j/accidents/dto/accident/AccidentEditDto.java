package ru.job4j.accidents.dto.accident;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentEditDto {
    @EqualsAndHashCode.Include
    private Long id;
    private Long typeId;
    private String name;
    private String text;
    private String address;
}