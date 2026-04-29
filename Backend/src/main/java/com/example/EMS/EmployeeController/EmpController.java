package com.example.EMS.EmployeeController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeService.EmpService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/employee")
public class EmpController {
	
	public EmpService empService;
	
	public EmpController(EmpService empService) {
		this.empService = empService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUserControll(@RequestBody Employee emp){
		return empService.createUser(emp);
		
	}
	
	@PostMapping("/registerEmp")
	public ResponseEntity<?> createUserImg(@RequestPart("employee") Employee emp, 
			@RequestPart(value= "file", required=true) MultipartFile file,
			@RequestPart(value= "passbook", required=true) MultipartFile passbook,
			@RequestPart(value= "education", required= true) MultipartFile education,
			@RequestPart(value="resume", required= true) MultipartFile resume,
			@RequestPart(value="offerLetter", required= true) MultipartFile offerLetter,
			@RequestPart(value="experienceLetter", required= true) List<MultipartFile> experienceLetter){
		
		
		return empService.createEmpIMG(emp, file, passbook, education, resume, offerLetter,experienceLetter);
	}
	
	
	@GetMapping("/getData")
	public ResponseEntity<?> getAllEmployeeDetails(){
		
		return empService.getAllEmployeeDetails();
	}
	
	@GetMapping("/getPayroll/{empId}")
	public ResponseEntity<?> getEmployeePayrollById(@PathVariable String empId){
		return empService.getPayrollById(empId);
		
	}
	
	
	
	
	
	
	
	
	
	

}
