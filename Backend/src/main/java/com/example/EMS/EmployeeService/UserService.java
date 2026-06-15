package com.example.EMS.EmployeeService;


import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.EMS.EmployeeDTO.LoginRequest;
import com.example.EMS.EmployeeDTO.LoginResponse;
import com.example.EMS.EmployeeEntity.ResetPassword;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeRepository.ResetPasswordRepository;
import com.example.EMS.EmployeeRepository.UserRepository;
import com.example.EMS.EmployeeSecurity.Jwtutil;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private JavaMailSender mailSender;
	private final Jwtutil jwt;
	private ResetPasswordRepository resetRepository;
	

	

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender,
			Jwtutil jwt, ResetPasswordRepository resetRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.jwt = jwt;
		this.resetRepository = resetRepository;
	}

	public ResponseEntity<?> createUser(@RequestBody User user){		
		
		if(user.getEmail() == null) {
			return  ResponseEntity.status(404).body("Please enter email id");
		}
		
		if(user.getPassword() == null) {
			return  ResponseEntity.status(404).body("Please enter password");
		}
		
		if(user.getConfirmPassword() == null) {
			return  ResponseEntity.status(404).body("Please enter confirm password");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			return  ResponseEntity.status(404).body("Password and confirm is not matching");
		}
		
		
		Optional<User> emailuser = userRepository.findByEmail(user.getEmail());
		
		if(emailuser.isPresent()) {
			return  ResponseEntity.status(409).body("User Already exists please login");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
		User admin = userRepository.save(user);
		return ResponseEntity.ok(admin);
		
	}
	
		public ResponseEntity<?> getUserById(Long id){
			
			Optional<User> user = userRepository.findById(id);
			
			if(user.isPresent()) {
				return ResponseEntity.status(302).body(user);
			}
			else {
				return ResponseEntity.status(404).body("User not found");
			}
			
		
		
		}
		
		public ResponseEntity<?> empLoginService(
		        @RequestBody LoginRequest login) {

		    Optional<User> user =
		            userRepository.findByEmail(login.getEmail());

		    if (user.isEmpty()) {

		        return ResponseEntity
		                .status(HttpStatus.UNAUTHORIZED)
		                .body("User not found");
		    }
            
		    User existingUser = user.get();
		    
		    if(!existingUser.isActive()) {
		    	return ResponseEntity
		                .status(HttpStatus.UNAUTHORIZED)
		                .body("User not active");
		    }

		    if (!passwordEncoder.matches(
		            login.getPassword(),
		            existingUser.getPassword())) {
		    	
		    	

		        return ResponseEntity
		                .status(HttpStatus.UNAUTHORIZED)
		                .body("Invalid password");
		    }

		    String token = jwt.generateToken(existingUser.getEmail());

		    LoginResponse response = new LoginResponse();

		    response.setToken(token);
		    response.setName(existingUser.getName());
		    response.setEmail(existingUser.getEmail());

		    
		    response.setRole(existingUser.getRoles());

		    return ResponseEntity.ok(response);
		}

		public ResponseEntity<?> sendMail(String empId, User user) {

		    try {

		        if (user == null || user.getEmail() == null) {
		            return ResponseEntity.badRequest()
		                    .body("User email not found");
		        }

		        String token = UUID.randomUUID().toString();

		        String rawPassword = "Temp@"+UUID.randomUUID().toString().substring(0,4);
		        
		        System.out.println(user.getEmail());
		        System.out.println(rawPassword);
		        user.setPassword(passwordEncoder.encode(rawPassword));
		        user.setConfirmPassword(user.getPassword());

		        userRepository.save(user);
		        
		        ResetPassword reset = new ResetPassword();
		        reset.setEmail(user.getEmail());
		        reset.setToken(token);
		        resetRepository.save(reset);
		        
		        
		        

		        String resetLink =
		                "http://localhost:3000/Login?token=" + token;

		        MimeMessage message = mailSender.createMimeMessage();

		        MimeMessageHelper helper =
		                new MimeMessageHelper(message, true);

		        helper.setTo(user.getEmail());
		        helper.setSubject("Set Your Password - Zenfuture Technologies");

		        String htmlContent = """
		        <!DOCTYPE html>
		        <html>
		        <body style="margin:0;padding:0;background:#f0ede8;font-family:Arial,Helvetica,sans-serif;">

		        <table width="100%%" cellpadding="0" cellspacing="0" border="0"
		               style="background:#f0ede8;padding:16px;">
		            <tr>
		                <td align="center">

		                    <table width="600" cellpadding="0" cellspacing="0"
		                           style="background:#ffffff;border-radius:8px;overflow:hidden;">

		                        <tr>
		                            <td style="background:#0f1117;padding:25px;">

		                                <img src="https://zenfuture.in/assets/images/logo.png"
		                                     width="60"
		                                     style="background:white;padding:8px;border-radius:5px;">

		                                <h2 style="color:white;margin-top:15px;">
		                                    Zenfuture Technologies
		                                </h2>

		                                <p style="color:#60a5fa;">
		                                    Employee Account Activation
		                                </p>

		                            </td>
		                        </tr>

		                        <tr>
		                            <td style="padding:30px;">

		                                <h2 style="color:#111827;">
		                                    Set Your Password
		                                </h2>

		                                <p style="color:#6b7280;line-height:1.8;">
		                                    Hello %s,
		                                </p>

		                                <p style="color:#6b7280;line-height:1.8;">
		                                    Your employee account has been created successfully.
		                                    Please use the following credentials to login.
		                                </p>

		                                <div style="
		                                    background:#f8fafc;
		                                    border:1px solid #cbd5e1;
		                                    padding:15px;
		                                    border-radius:8px;
		                                    margin:20px 0;">

		                                    <p style="margin:0 0 10px 0;">
		                                        <strong>Email:</strong> %s
		                                    </p>
		        							
		        							   <p style="margin:0;">
										        <strong>Temporary Password:</strong> %s
										    </p>
		                            

		                                </div>

		                                <div style="margin:30px 0;text-align:center;">
		                                    <a href="%s"
		                                       style="
		                                       background:#2563eb;
		                                       color:white;
		                                       text-decoration:none;
		                                       padding:14px 25px;
		                                       border-radius:6px;
		                                       font-weight:bold;
		                                       display:inline-block;">
		                                       Set Password
		                                    </a>
		                                </div>

		                                <p style="font-size:13px;color:#6b7280;">
		                                    If the button doesn't work, copy and paste this URL into your browser:
		                                </p>

		                                <p>
		                                    <a href="%s">%s</a>
		                                </p>

		                                <div style="
		                                    background:#fffbeb;
		                                    border:1px solid #fde68a;
		                                    padding:12px;
		                                    border-radius:6px;
		                                    color:#92400e;">
		                                    This link is valid for 24 hours.
		                                </div>

		                                <p style="margin-top:25px;color:#6b7280;">
		                                    If you did not request this email,
		                                    please ignore it.
		                                </p>

		                            </td>
		                        </tr>

		                        <tr>
		                            <td style="
		                            background:#f9fafb;
		                            padding:20px;
		                            border-top:1px solid #e5e7eb;">

		                                <strong>HR – Talent Acquisition Group</strong><br>
		                                Zenfuture Technologies<br><br>

		                                📞 +91-9092979396<br>
		                                📧 tag@zenfuture.in<br>
		                                🌐 www.zenfuture.in

		                            </td>
		                        </tr>

		                    </table>

		                </td>
		            </tr>
		        </table>

		        </body>
		        </html>
		        """.formatted(
		                user.getName() != null ? user.getName() : "Employee",
		                user.getEmail(),
		                rawPassword,
		                resetLink,
		                resetLink,
		                resetLink
		        );

		        helper.setText(htmlContent, true);

		        mailSender.send(message);

		        return ResponseEntity.ok(
		                "Password setup mail sent successfully to "
		                        + user.getEmail() + "\ntoken: "+token + "\nOTP: "+rawPassword 
		        );

		    } catch (Exception e) {

		        e.printStackTrace();

		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Failed to send email : " + e.getMessage());
		    }
		}
		
		
		public ResponseEntity<?> resetPassword(String email, User user, ResetPassword passwordDTO){
			
//			 if(!passwordEncoder.matches(password.getOneTimePassword(), user.getPassword())) {
//				 return ResponseEntity.badRequest().body("One time password not matched");
//			 }
			//Optional<ResetPassword> opt =  resetRepository.findByEmail(email);
			Optional<ResetPassword> opt =  resetRepository.findByToken(passwordDTO.getToken());
			
			if(opt.isEmpty()) {
				return ResponseEntity.badRequest().body("Mail not sent to user");
			}
			
			ResetPassword password = opt.get();
			
			
			if(password.isUsed()) {
				return ResponseEntity.badRequest().body("Password reset already used");
			}
			System.out.println("Token : " + passwordDTO.getToken());
			System.out.println("Password : " + passwordDTO.getPassword());
			System.out.println("Confirm : " + passwordDTO.getConfirmPassword());
			password.setUsed(true);
			password.setPassword(passwordDTO.getPassword());
			user.setPassword(passwordEncoder.encode(password.getPassword()));
			
			userRepository.save(user);
			 
			 return ResponseEntity.ok("Your password has been reset");
		
		}
	
	
	 

}
