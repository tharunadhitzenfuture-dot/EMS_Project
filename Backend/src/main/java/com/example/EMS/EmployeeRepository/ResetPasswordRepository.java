package com.example.EMS.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
	
	Optional<ResetPassword> findByEmail(String email);
	//Optional<ResetPassword> findByToken(String token);
	Optional<ResetPassword> findByOtp(String otp);
	void deleteByEmail(String email);
	 void deleteByExpiryTimeBefore(LocalDateTime time);

}
