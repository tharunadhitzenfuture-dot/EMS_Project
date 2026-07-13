package com.example.EMS.EmployeeController;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.ForgotPasswordDTO;
import com.example.EMS.EmployeeDTO.LoginRequest;
import com.example.EMS.EmployeeDTO.ResetPasswordDTO;
import com.example.EMS.EmployeeDTO.UserControlDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.UserRepository;
import com.example.EMS.EmployeeService.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	private EmpRepository empRepository;
	private UserRepository userRepository;


	public UserController(UserService userService, EmpRepository empRepository, UserRepository userRepository) {
		this.userService = userService;
		this.empRepository = empRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUserControll(@RequestBody User user){
		return userService.createUser(user);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id){
		 return userService.getUserById(id);

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	    return userService.empLoginService(request);
	}
	
//	@PostMapping("/sendPasswordMail")
//	public ResponseEntity<?> sendPasswordMail(@RequestParam String empId){		
//		Optional<Employee>  emp =empRepository.findByEmployeeId(empId);
//		
//		if(emp.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: "+empId);
//		}
//		
//		
//		Employee e = emp.get();
//		
//		if(emp.get().getUser() == null) {
//			User usr = new User();
//			usr.setEmail(e.getEmail());
//			e.setUser(usr);
//		}
//		
//		User user = e.getUser();
//		
//		return userService.sendMail(empId, user);
//		
//		
//		
//		
//	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO passwordReq){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		Optional<Employee> empUser = empRepository.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User employee details not found");
		}
		
		String email = empUser.get().getEmail();
		
		if(passwordReq.getOldPassword() == null || passwordReq.getOldPassword().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter old password");
		}
		
		if(passwordReq.getPassword() == null || passwordReq.getPassword().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter password");
		}
		
		if(passwordReq.getConfirmPassword() == null || passwordReq.getConfirmPassword().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter confirm password");
		}

//		Optional<Employee>  emp =empRepository.findByEmail(email);
		
//		if(emp.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with email: "+passwordReq.getEmail());
//		}
//		
//		
//		if(user == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created with id: "+passwordReq.getEmail());
//		}
		
		return userService.resetPassword(email, user, passwordReq);
		
	}
	
	@PostMapping("/sendForgetPasswordMail")
	public ResponseEntity<?> sendPasswordMail(@RequestBody ForgotPasswordDTO req){		
		
		if(req.getEmail() == null || req.getEmail().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter email");
		}
		
		Optional<Employee>  emp = empRepository.findByEmail(req.getEmail());

		if(emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with email: "+req.getEmail());
		}
		
		
		Employee e = emp.get();
		
		if(!e.getProfessional_details().getEmp_status().equalsIgnoreCase("Active")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not active found with email: "+req.getEmail());
		}
		
		if(emp.get().getUser() == null) {
			User usr = new User();
			usr.setEmail(e.getEmail());
			e.setUser(usr);
		}
		
		User user = e.getUser();
		
		return userService.sendMail(emp.get().getEmployeeId() , user);
	
		
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestBody ForgotPasswordDTO request){
		
		if(request.getEmail() == null || request.getEmail().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter email");
		}
		if(request.getOtp() == null || request.getOtp().length() ==0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter OTP");
		}

		Optional<Employee>  emp =empRepository.findByEmail(request.getEmail());
		
		if(emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with email: "+request.getEmail());
		}
		
		User user = emp.get().getUser();
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created with id: "+request.getEmail());
		}
		
		return userService.verifyOTP(request.getEmail(), user, request);
		
		
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody ForgotPasswordDTO passwordReq){
		
		if(passwordReq.getEmail() == null || passwordReq.getEmail().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter email");
		}
		
		if(passwordReq.getPassword() == null || passwordReq.getPassword().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter password");
		}
		
		if(passwordReq.getConfirmPassword() == null || passwordReq.getConfirmPassword().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter confirm password");
		}
		
		User user = userRepository.findByEmail(passwordReq.getEmail()).get();


		Optional<Employee>  emp =empRepository.findByEmail(passwordReq.getEmail());
		
		if(emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with email: "+passwordReq.getEmail());
		}
		
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created with id: "+passwordReq.getEmail());
		}
		
		System.out.println("Null "+passwordReq.getPassword());
		
		
		return userService.resetForgetPassword(passwordReq.getEmail(), user, passwordReq);
		
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers(){
		List<User> lst =userRepository.findAll();
		return ResponseEntity.ok(lst);
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<?> getAllUsers(@PathVariable Long id){
		Optional<User> opt =userRepository.findById(id);
		if(opt.isEmpty()) {
			return ResponseEntity.badRequest().body("User not found with id:"+id);
		}
		return ResponseEntity.ok(opt.get());
	}
		
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
	    return userService.updateUser(id, user);
	}
	
	@GetMapping("/getDetails")
	public ResponseEntity<?> getEmployeeDetails(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		UserControlDTO dto = new UserControlDTO();
		dto.setUserId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setUserRole(user.getRoleEntity().getRole());
		dto.setActive(user.isActive());
//		dto.setPassword(user.getPassword());
		dto.setEmployee(user.getEmployee());
		
		return ResponseEntity.ok(dto);

	}
	
	public Employee getCurrentEmployee() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
		User user = (User) authentication.getPrincipal();
		
		Employee empUser = empRepository.findByUser(user).orElseThrow(()-> new RuntimeException("Employee not found for current user"));

		return empUser;	
	
	}
	

}
