package com.example.finance_dashboard.config;

import com.example.finance_dashboard.model.Role;
import com.example.finance_dashboard.repository.RoleRepository;
import com.example.finance_dashboard.repository.UserRepository;
import com.example.finance_dashboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create roles
        Role viewer  = createRoleIfMissing("ROLE_VIEWER");
        Role analyst = createRoleIfMissing("ROLE_ANALYST");
        Role admin   = createRoleIfMissing("ROLE_ADMIN");

        // Create admin user directly — no UserService dependency
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@finance.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setActive(true);
            adminUser.setRoles(Set.of(admin));
            userRepository.save(adminUser);
            System.out.println("✅ Admin created: admin / admin123");
        } else {
            System.out.println("ℹ️ Admin already exists.");
        }
    }

    private Role createRoleIfMissing(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}