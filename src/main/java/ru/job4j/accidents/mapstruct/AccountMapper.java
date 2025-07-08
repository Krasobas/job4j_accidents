package ru.job4j.accidents.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.accidents.dto.account.AccountCreateDto;
import ru.job4j.accidents.model.Account;
import ru.job4j.accidents.model.Role;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roles", target = "roles")
    @Mapping(target = "enabled", ignore = true)
    Account getEntityOnCreate(AccountCreateDto dto, Set<Role> roles);
}
