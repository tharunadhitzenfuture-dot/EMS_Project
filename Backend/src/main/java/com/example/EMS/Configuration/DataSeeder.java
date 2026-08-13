package com.example.EMS.EmployeeConfiguration;


import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EMS.Entity.User;
import com.example.EMS.Entity.Role.Role;
import com.example.EMS.Repository.UserRepository;
import com.example.EMS.Repository.RoleRepository.RoleRepository;

@Configuration
public class DataSeeder {
	
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;


	public DataSeeder(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
	
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}


	@Bean
	@Order(1)
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {

     
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                User user = new User();
                Role role = new Role();
                role.setRole("ADMIN");
                Role savedRole = roleRepository.save(role);
                user.setName("Admin");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin123")); 
                HashSet<String> roles = new HashSet<>();
                roles.add("ADMIN");
                user.setRoleEntity(savedRole);

                userRepository.save(user);
                
                

                System.out.println("✅ Default admin user created");
            } else {
                System.out.println("⚡ Admin already exists");
            }
        };
    }
}