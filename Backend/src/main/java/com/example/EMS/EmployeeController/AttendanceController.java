package com.example.EMS.EmployeeController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeService.AttendanceService;


@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	
	private AttendanceService attendanceService;
	private AttendanceRepository attendanceRepo;
	private EmpRepository empRepo;
	
	public AttendanceController(AttendanceService attendanceService, AttendanceRepository attendanceRepo, EmpRepository empRepo) {
		this.attendanceService = attendanceService;
		this.attendanceRepo = attendanceRepo;
		this.empRepo = empRepo;
	}



	@PostMapping("/register")
	public ResponseEntity<?> registerAttendance(
	        @RequestBody List<AttendanceRequestDTO> requests){

	    List<String> responseList = new ArrayList<>();

	    for(AttendanceRequestDTO request : requests){

	        // Check empId
	        if(request.getEmpId() == null){

	            responseList.add(
	                    "Employee ID is missing");

	            continue;
	        }

	        
	     
	        Optional<Employee> emp =
	                empRepo.findByEmployeeId(
	                        request.getEmpId());

	        if(emp.isEmpty()){

	            responseList.add(
	                    "Employee not found : "
	                    + request.getEmpId());

	            continue;
	        }

	        
	   
	        ResponseEntity<?> response =
	                attendanceService.registerService(
	                        emp.get(),
	                        request.getDate(),
	                        request.getCheckIn(),
	                        request.getCheckOut(),
	                        request.getStatus());

	        responseList.add(response.getBody().toString());
	    }

	    return ResponseEntity.ok(responseList);
	}
	
	 @GetMapping("/getAllAttendance")
	    public ResponseEntity<?> getAllAttendance(){

	        return attendanceService.getAllAttendance();
	    }


	    
	    @GetMapping("/getAllAttendance/{empId}")
	    public ResponseEntity<?> getAttendanceById(
	            @PathVariable String empId){

	        return attendanceService.getAttendanceById(empId);
	    }


	   
	    @DeleteMapping("/delete/{empId}")
	    public ResponseEntity<?> deleteAttendanceById(
	            @PathVariable String empId){

	        return attendanceService.deleteAttendanceById(empId);
	    }
	
	    @PatchMapping("/update/{empId}")
	    public ResponseEntity<?> updateAttendance(
	            @PathVariable String empId,
	            @RequestBody AttendanceRequestDTO request){
	    	

	        Optional<Employee> emp =
	                empRepo.findByEmployeeId(
	                        request.getEmpId());

	        if(emp.isEmpty()){
	        	return ResponseEntity.badRequest().body("Employee not found with id: "+empId);
	        }

	        

	        return attendanceService.updateAttendance(
	        		emp.get(),
	                empId,
	                request);
	    }
	    
	    @PostMapping("/getworkingHours")
	    public ResponseEntity<?> getWorkingHoursById(@RequestBody WorkingHoursDTO request){
	    	if (request.getEmpId() == null || request.getEmpId().isBlank()) {
	    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    	            .body("Please enter employee id");
	    	}

	    	if (request.getStartDate() == null) {
	    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    	            .body("Please enter start date");
	    	}

	    	if (request.getEndDate() == null) {
	    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    	            .body("Please enter end date");
	    	}
	    	return attendanceService.getWeeklyHours(request);
	    }
	    
	    @GetMapping("/totalEmpPresent/{date}")
	    public ResponseEntity<?> getTotalEmpPresent(@PathVariable LocalDate date) {
	    	
	    	if(date == null) {
	    		return ResponseEntity.badRequest().body("Please enter date");
	    	}
	    	
	    	return attendanceService.getEmpPresent(date);
	    	
	    }
	    
	    
	    @GetMapping("/getHoursWeekly/{empId}")
	    public ResponseEntity<?> getHoursWeekly(
	            @PathVariable String empId){
	    	
	    	if(empId == null) {
	    		return ResponseEntity.badRequest().body("Employee id is null");
	    	}

	        return attendanceService.calculateWeeklyHours(empId);
	    }

	    
	    
	  @GetMapping("/getWeeklyReport")
	  public ResponseEntity<?> getWeeklyReport(){
		  
		 List<Employee> lst =  empRepo.findAll();
		 List<String> ids = new ArrayList<>();
		 
		 for(Employee emp: lst) {
			String id =  emp.getEmployeeId();
			ids.add(id);
		 }
		 
		 
		 return attendanceService.getWeeklyReport(ids);
		  
		  
	  }

	

}
