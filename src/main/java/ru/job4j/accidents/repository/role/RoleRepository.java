package ru.job4j.accidents.repository.role;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
