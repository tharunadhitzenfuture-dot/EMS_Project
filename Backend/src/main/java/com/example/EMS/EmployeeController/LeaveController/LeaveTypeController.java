package com.example.EMS.EmployeeController.LeaveController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.LeaveTypeDTO;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeavePolicyRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveTypeRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveTypeService;

@RestController
@RequestMapping("/api/leaveType")
public class LeaveTypeController {

	private LeaveTypeService service;
	private LeaveTypeRepository repository;
	private LeavePolicyRepository leavePolicyRepository;
	
	
	
	public LeaveTypeController(LeaveTypeService service, LeaveTypeRepository repository,
			LeavePolicyRepository leavePolicyRepository) {
		this.service = service;
		this.repository = repository;
		this.leavePolicyRepository = leavePolicyRepository;
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
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllType(){
		List<LeaveTypeDTO> list  = new ArrayList<>();
		List<LeaveType> types = repository.findAll();
		
		for(LeaveType type: types) {
			LeaveTypeDTO dto = new LeaveTypeDTO();
			dto.setId(type.getId());
			dto.setName(type.getName());
			list.add(dto);
		}
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteTypeById(@PathVariable Long id){
		if(id ==  null) {
			return ResponseEntity.badRequest().body("Please enter type id");
		}
		Optional<LeaveType> type  = repository.findById(id);
		if(type.isEmpty()) {
			return ResponseEntity.badRequest().body("No Leavetype with id: "+id);
		}
		
		 if (leavePolicyRepository.existsByLeaveType(type.get())) {
		        return ResponseEntity.badRequest()
		                .body("Cannot delete. Leave Type is used in Leave Policy.");
		    }
		 
		 repository.deleteById(id);
		 return ResponseEntity
	                .ok("Successfully deleted");
		 
	}
	
	
}
