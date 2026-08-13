package com.example.EMS.Service.Impl.LeaveService;

import com.example.EMS.Service.LeaveService.LeaveTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.LeaveTypeDTO;
import com.example.EMS.Entity.LeaveEntity.LeaveType;
import com.example.EMS.Repository.LeaveRepository.LeaveTypeRepository;

@Service
@AllArgsConstructor
public class LeaveTypeServiceImpl implements LeaveTypeService {
	
	private final LeaveTypeRepository repository;

	public ResponseEntity<?> createLeaveType(LeaveType type){
		
		LeaveType saved = repository.save(type);
		LeaveTypeDTO dto  = new LeaveTypeDTO();
		dto.setId(saved.getId());
		dto.setName(saved.getName());
		return ResponseEntity.ok(dto);
	}
	

}
