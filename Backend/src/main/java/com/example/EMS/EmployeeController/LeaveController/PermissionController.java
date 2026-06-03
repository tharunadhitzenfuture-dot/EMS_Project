package com.example.EMS.EmployeeController.LeaveController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.EmployeeService.PermissionService;

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
		if(request.getStartDate() == null || request.getEndDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide week start date and end date");
		}
		
		if(request.getHours() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide week permission hours");
		}
		
		
		return  permissionService.applyPermission(empId, request);
		
	}
	
	

}
