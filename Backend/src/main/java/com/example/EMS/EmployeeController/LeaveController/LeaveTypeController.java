package com.example.EMS.EmployeeController.LeaveController;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveTypeRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveTypeService;

@RestController
@RequestMapping("/api/leaveType")
public class LeaveTypeController {

	private LeaveTypeService service;
	private LeaveTypeRepository repository;
	
	public LeaveTypeController(LeaveTypeService service, LeaveTypeRepository repository) {
		this.service = service;
		this.repository = repository;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createLeaveType(@RequestBody LeaveType type){
		
		if(type.getName() == null || type.getName().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter leave type");
		}
		Optional<LeaveType>  types = repository.findByName(type.getName());
		if(types.isPresent()) {
			return ResponseEntity.badRequest().body("Leave type already presented "+type.getName());
		}
		
		
		return service.createLeaveType(type);
		
	}
	
	
}
