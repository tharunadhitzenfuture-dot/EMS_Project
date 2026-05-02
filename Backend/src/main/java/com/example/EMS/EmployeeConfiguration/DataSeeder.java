package com.example.EMS.EmployeeConfiguration;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeRepository.UserRepository;



@Configuration
public class DataSeeder {
	
	private final PasswordEncoder passwordEncoder;
	
	

    public DataSeeder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}



	@Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {

     
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

                User user = new User();
                user.setName("Admin");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin123")); 
                user.setUserRole("ADMIN");

                userRepository.save(user);

                System.out.println("✅ Default admin user created");
            } else {
                System.out.println("⚡ Admin already exists");
            }
        };
    }
}