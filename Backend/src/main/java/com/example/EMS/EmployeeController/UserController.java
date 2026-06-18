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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.LoginRequest;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.ResetPassword;
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
	
	@PostMapping("/sendPasswordMail")
	public ResponseEntity<?> sendPasswordMail(@RequestParam String empId){		
		Optional<Employee>  emp =empRepository.findByEmployeeId(empId);
		
		if(emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: "+empId);
		}
		
		Employee e = emp.get();
		
		if(emp.get().getUser() == null) {
			User usr = new User();
			usr.setEmail(e.getEmail());
			e.setUser(usr);
		}
		
		User user = e.getUser();
		
		return userService.sendMail(empId, user);
		
		
		
		
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPassword passwordReq){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		Optional<Employee> empUser = empRepository.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
		}
		
		String email = empUser.get().getEmail();

		Optional<Employee>  emp =empRepository.findByEmail(email);
		
		if(emp.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with email: "+passwordReq.getEmail());
		}
		
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created with id: "+passwordReq.getEmail());
		}
		
		System.out.println("Null "+passwordReq.getPassword());
		
		
		return userService.resetPassword(email, user, passwordReq);
		
		
		
		
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
		
		Optional<Employee> empUser = empRepository.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
		}
		
		String empId = empUser.get().getEmployeeId();
		
		Optional<Employee>  emp = empRepository.findByEmployeeId(empId);
		return ResponseEntity.ok(emp);

	}
	

}
