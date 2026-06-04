package com.example.EMS.EmployeeController.LeaveController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.EmployeeService.PermissionService;
import com.example.EMS.enums.LeaveType;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
	
	private final PermissionRepository permissionRepo;
	private final PermissionService permissionService;
	
	public PermissionController(PermissionRepository permissionRepo, PermissionService permissionService) {
		this.permissionRepo = permissionRepo;
		this.permissionService = permissionService;
	}
	
	
	@PostMapping("/apply/{empId}")
	public ResponseEntity<?> applyLeave(@PathVariable String empId, @RequestBody Permission request){
		if(request.getPermissionDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide permission date");
		}
		
		if(request.getHours() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide week permission hours");
		}
		
		
		LocalDate permissionDate = request.getPermissionDate();

		LocalDate startOfWeek = permissionDate.with(
		        TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		LocalDate endOfWeek = permissionDate.with(
		        TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		
		request.setStartDate(startOfWeek);
		request.setEndDate(endOfWeek);
		
		
		return  permissionService.applyPermission(empId, request);
		
	}
	
	
	 @PutMapping("/review/{empId}/{permissionId}")
	 public ResponseEntity<?> reviewLeave(@PathVariable String empId,@PathVariable Long permissionId,@RequestBody Permission dto) {
		
		if(empId == null || permissionId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide employee id and permission id");
		}
		
		if(dto.getStatus() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide approval status APPROVED, REJECTED");
		}
		
		
		
		return permissionService.reviewPermission(empId, permissionId, dto);
	}
	
	
	

}
