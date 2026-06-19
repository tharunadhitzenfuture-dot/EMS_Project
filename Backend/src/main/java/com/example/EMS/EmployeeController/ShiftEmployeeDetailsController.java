package com.example.EMS.EmployeeController;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.ShiftEmployeeDetails;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.ShiftEmployeeDetailsRepository;
import com.example.EMS.EmployeeService.ShiftEmployeeDetailsService;

@RestController
@RequestMapping("/api/employeeShift")
public class ShiftEmployeeDetailsController {
	
	private final ShiftEmployeeDetailsService shiftService;
	private final ShiftEmployeeDetailsRepository shiftRepo;
	private final EmpRepository empRepository;
	
	
	
	public ShiftEmployeeDetailsController(ShiftEmployeeDetailsService shiftService,
			ShiftEmployeeDetailsRepository shiftRepo, EmpRepository empRepository) {
		this.shiftService = shiftService;
		this.shiftRepo = shiftRepo;
		this.empRepository = empRepository;
	}


//	@PostMapping("/createShift")
//	public ResponseEntity<?> createShift(@RequestBody ShiftEmployeeDetails request){
//		
//		if(request.getEmpId() == null || request.getEmpId().length() ==0) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter employee id");
//		}
//		
//		Optional<Employee> emp = empRepository.findByEmployeeId(request.getEmpId());
//		
//		if(emp.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: "+request.getEmpId());
//		}
//		
//		Employee e = emp.get();
//		request.setEmail(e.getEmail());
//		request.setName(e.getFirst_name()+" "+e.getLast_name());
//		request.setDept(e.getProfessional_details().getProfessional_department());
//		
//
//		if (request.getShift() == null || request.getShift().trim().isEmpty()) {
//		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//		            .body("Please enter shift");
//		}
//
//		if (request.getStartTime() == null) {
//		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//		            .body("Please enter start date");
//		}
//
//		if (request.getEndTime() == null) {
//		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//		            .body("Please enter end date");
//		}
//		
//		return shiftService.create(request);
//		
//		
//	}
//	
	
	@PostMapping(value = "/uploadExcel", consumes = "multipart/form-data")
	public ResponseEntity<?> register(@RequestPart(value = "xlFile") MultipartFile xlFile) throws Exception{
		return ResponseEntity.ok(shiftService.bulkUpload(xlFile));
		
	}
	
	

}
