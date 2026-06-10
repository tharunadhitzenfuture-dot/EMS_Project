package com.example.EMS.EmployeeController.LeaveController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.EmployeeService.PermissionService;


@RestController
@RequestMapping("/api/permission")
public class PermissionController {
	
	private final PermissionRepository permissionRepo;
	private final PermissionService permissionService;
	private final EmpRepository empRepo;
	
	
	
	
	public PermissionController(PermissionRepository permissionRepo, PermissionService permissionService,
			EmpRepository empRepo) {
		
		this.permissionRepo = permissionRepo;
		this.permissionService = permissionService;
		this.empRepo = empRepo;
	}


	@PostMapping("/apply/{empId}")
	public ResponseEntity<?> applyLeave(@PathVariable String empId, @RequestBody Permission request){
		if(request.getPermissionDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide permission date");
		}
		
		Long id = empRepo.findIdByEmployeeId(empId);
		
		if(request.getHours() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide week permission hours");
		}
		
		
		LocalDate permissionDate = request.getPermissionDate();
		
		Optional<Permission> opt = permissionRepo.findByPermissionDateAndEmployee_Id(permissionDate,id);
		if(opt.isPresent()) {
			return ResponseEntity.badRequest().body("Permission already applied for date :"+permissionDate);
		}

		if(request.getStartDate() == null && request.getEndDate() == null) {
			LocalDate startOfWeek = permissionDate.with(
			        TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

			LocalDate endOfWeek = permissionDate.with(
			        TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
			
			request.setStartDate(startOfWeek);
			request.setEndDate(endOfWeek);
		}
		
		
		
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
	 
	 
	 @GetMapping("/getAllPermission")
	 public ResponseEntity<?> getAllPermission() {
		List<Permission> list  = permissionService.getAllPermission();
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Permission requests list empty");
		}
		return ResponseEntity.ok(list);
	}
	 
	 @GetMapping("/getPermissionById/{id}")
	 public ResponseEntity<?> getAllPermission(@PathVariable Long id) {
		
		 if(id == null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter permission id");
		 }
		Permission permission = permissionService.getPermissionById(id);
		if(permission == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Permission not found with id: "+id);
		}
		return ResponseEntity.ok(permission);
	}
	 
	 @GetMapping("/getListPermissionById/{empId}")
	 public ResponseEntity<?> getListPermission(@PathVariable Long empId) {
		
		 if(empId == null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter employee id");
		 }
		List<Permission> permission = permissionService.getListPermissionById(empId);
		if(permission == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Permission not found with id: "+empId);
		}
		return ResponseEntity.ok(permission);
	}
	
	 @PutMapping("/update/{empId}/{permissionId}")
	 public ResponseEntity<?> updatePermission(
	         @PathVariable String empId,
	         @PathVariable Long permissionId,
	         @RequestBody Permission request) {

	     if (empId == null || permissionId == null) {
	         return ResponseEntity.badRequest()
	                 .body("Please provide employee id and permission id");
	     }

	     return permissionService.updatePermission(
	             empId,
	             permissionId,
	             request
	     );
	 }
	
	 @DeleteMapping("/delete/{empId}/{permissionId}")
	 public ResponseEntity<?> deletePermission(
	         @PathVariable String empId,
	         @PathVariable Long permissionId) {

	     if (empId == null || permissionId == null) {
	         return ResponseEntity.badRequest()
	                 .body("Please provide employee id and permission id");
	     }

	     return permissionService.deletePermission(
	             empId,
	             permissionId
	     );
	 }
	 

}
