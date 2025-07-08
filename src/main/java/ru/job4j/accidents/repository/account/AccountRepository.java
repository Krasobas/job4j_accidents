package ru.job4j.accidents.repository.account;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Account;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
