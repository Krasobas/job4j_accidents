package ru.job4j.accidents.service.account;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Account;
import ru.job4j.accidents.repository.account.AccountRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        Account account = accountOptional.get();
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRoles().stream()
                        .map(role -> role.getName().toUpperCase())
                        .toArray(String[]::new))
                .build();
    }
}
