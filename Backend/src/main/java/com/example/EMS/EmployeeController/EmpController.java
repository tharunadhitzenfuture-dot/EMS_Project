package com.example.EMS.EmployeeController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			@RequestPart(value= "file", required=false) MultipartFile file,
			@RequestPart(value= "passbook",required=false) MultipartFile passbook,
			@RequestPart(value= "education",required=false) MultipartFile education,
			@RequestPart(value="resume",required=false) MultipartFile resume,
			@RequestPart(value="offerLetter",required=false) MultipartFile offerLetter,
			@RequestPart(value="experienceLetter",required=false) List<MultipartFile> experienceLetter){
		
		
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
	
	@GetMapping("/getEmployee/{empId}")
	public ResponseEntity<?> getEmployeeById(Long empId){
		return empService.getEmployeeById(empId);
		
	}
	
	@DeleteMapping("/deleteEmployee/{empId}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable String empId){
		return empService.deleteEmployeeById(empId);
		
	}
	
	@PutMapping("/updateEmployee/{empId}")
	public ResponseEntity<?> updateEmployeeById(@PathVariable String empId, @RequestBody Employee emp){
		return empService.updateEmployee(empId, emp);
	}
	
	
	@PutMapping("/updateEmployee/{empId}/image")
	public ResponseEntity<?> updateEmployeeImage(
	        @PathVariable String empId,
	        @RequestPart("image") MultipartFile image) throws Exception {
	    return empService.updateEmployeeImage(empId, image);
	}
	
	@PutMapping("/updateEmployee/{empId}/resume")
	public ResponseEntity<?> updateEmployeeResume(@PathVariable String empId,
	        @RequestPart("resume") MultipartFile resume) throws Exception{
		return empService.updateEmployeeFile(empId, resume, "resume");
	}
	
	@PutMapping("/updateEmployee/{empId}/offerLetter")
	public ResponseEntity<?> updateOfferLetter(
	        @PathVariable String empId,
	        @RequestPart("offerLetter") MultipartFile offerLetter) throws Exception{
	    return empService.updateEmployeeFile(empId, offerLetter, "offerLetter");
	}

	@PutMapping("/updateEmployee/{empId}/passbookPdf")
	public ResponseEntity<?> updatePassbookPdf(
	        @PathVariable String empId,
	        @RequestPart("passbookPdf") MultipartFile passbookPdf) throws Exception {
	    return empService.updateEmployeeFile(empId, passbookPdf, "passbookPdf");
	}

	@PutMapping("/updateEmployee/{empId}/educationPdf")
	public ResponseEntity<?> updateEducationPdf(
	        @PathVariable String empId,
	        @RequestPart("educationPdf") MultipartFile educationPdf) throws Exception {
	    return empService.updateEmployeeFile(empId, educationPdf, "educationPdf");
	}

	@PutMapping("/updateEmployee/{empId}/expLetter")
	public ResponseEntity<?> updateExpLetter(
	        @PathVariable String empId,
	        @RequestPart("expLetter") MultipartFile expLetter) throws Exception {
	    return empService.updateEmployeeFile(empId, expLetter, "expLetter");
	}
	
	
	
	
	
	
	
	
	
	

}
