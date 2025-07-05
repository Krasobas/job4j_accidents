package ru.job4j.accidents.dto.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RuleDto {
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
}
