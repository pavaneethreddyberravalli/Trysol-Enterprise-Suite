package org.trysol.Trysol.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.trysol.Trysol.Auth.Repository.RoleRepository;

import org.trysol.Trysol.Auth.entity.Role;

import java.util.List;
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository rolerepository;
    @Override
    public void run(String... args) throws Exception {
        List<String> roles= List.of(
        "ADMIN", "HR", "FINANCE",
                "SALES", "TRAINER",
                "ASSET_ADMIN", "MANAGER");
        roles.forEach(roleName -> {
            if (rolerepository.findByName(roleName).isEmpty()) {
                rolerepository.save(new Role(null, roleName));
            }
        });
    }
}
