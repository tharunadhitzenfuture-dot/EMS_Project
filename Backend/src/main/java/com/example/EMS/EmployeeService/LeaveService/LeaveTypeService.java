package com.example.EMS.EmployeeService.LeaveService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveTypeRepository;

@Service
public class LeaveTypeService {
	
	private LeaveTypeRepository repository;

	public LeaveTypeService(LeaveTypeRepository repository) {
		this.repository = repository;
	}
	
	public ResponseEntity<?> createLeaveType(LeaveType type){
		
		LeaveType saved = repository.save(type);
		return ResponseEntity.ok(saved);
	}
	

}
