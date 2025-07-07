package ru.job4j.accidents.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accident")
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "ACCIDENT_TYPE_ID_FK"))
    private AccidentType type;
    private String name;
    private String text;
    private String address;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "accident_rule",
            joinColumns = {
                    @JoinColumn(name = "accident_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "rule_id")
            }
    )
    private Set<Rule> rules;
}