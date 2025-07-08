package ru.job4j.accidents.service.account;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.account.AccountCreateDto;
import ru.job4j.accidents.mapstruct.AccountMapper;
import ru.job4j.accidents.model.Account;
import ru.job4j.accidents.model.Role;
import ru.job4j.accidents.repository.account.AccountRepository;
import ru.job4j.accidents.repository.role.RoleRepository;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AccountMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = repository.findByEmail(email);
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

    public Optional<Account> save(AccountCreateDto account) {
        account.setPassword(encoder.encode(account.getPassword()));
        Optional<Role> userRole = roleRepository.findByName("user");
        if (userRole.isEmpty()) {
            return Optional.empty();
        }
        Account toCreate = mapper.getEntityOnCreate(account, Set.of(userRole.get()));
        return Optional.ofNullable(repository.save(toCreate));
    }
}
