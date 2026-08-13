package com.example.EMS.Service.User;

import com.example.EMS.EmployeeDTO.ForgotPasswordDTO;
import com.example.EMS.EmployeeDTO.LoginRequest;
import com.example.EMS.EmployeeDTO.ResetPasswordDTO;
import com.example.EMS.Entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(User user);

    ResponseEntity<?> getUserById(Long id);

    ResponseEntity<?> empLoginService(LoginRequest login);

    ResponseEntity<?> sendMail(String empId, User user);

    ResponseEntity<?> verifyOTP(String email, User user, ForgotPasswordDTO passwordDTO);

    ResponseEntity<?> resetForgetPassword(String email, User user, ForgotPasswordDTO password);

    ResponseEntity<?> resetPassword(String email, User user, ResetPasswordDTO password);

    ResponseEntity<?> updateUser(Long id, User updatedUser);

    void deleteExpiredOtps();

}