package com.example.EMS.EmployeeService.LeaveService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.LeaveTypeDTO;
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
		LeaveTypeDTO dto  = new LeaveTypeDTO();
		dto.setId(saved.getId());
		dto.setName(saved.getName());
		return ResponseEntity.ok(dto);
	}
	

}
