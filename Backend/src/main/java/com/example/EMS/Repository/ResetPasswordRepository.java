package com.example.EMS.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.EMS.Entity.ResetPassword;




public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
	
	Optional<ResetPassword> findByEmail(String email);
	//Optional<ResetPassword> findByToken(String token);
	Optional<ResetPassword> findByOtp(String otp);
	void deleteByEmail(String email);
	@Transactional
    @Modifying
    @Query("DELETE FROM ResetPassword r WHERE r.expiryTime < :time")
	int deleteByExpiryTimeBefore(LocalDateTime time);

}
