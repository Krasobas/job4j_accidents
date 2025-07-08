package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Account;
import ru.job4j.accidents.model.Role;
import ru.job4j.accidents.repository.account.AccountRepository;
import ru.job4j.accidents.repository.role.RoleRepository;

import java.util.Set;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.findByName("admin").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("admin");
            roleRepository.save(adminRole);
        }

        if (accountRepository.findByEmail("admin@exemple.com").isEmpty()) {
            roleRepository.findByName("admin").ifPresent(role -> {
                Account admin = new Account();
                admin.setName("admin");
                admin.setEmail("admin@exemple.com");
                admin.setRoles(Set.of(role));
                admin.setPassword(encoder.encode("admin"));
                accountRepository.save(admin);
            });

        }
    }
}