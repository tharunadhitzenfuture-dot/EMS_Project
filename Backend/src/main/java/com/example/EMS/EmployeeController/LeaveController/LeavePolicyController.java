package com.example.EMS.EmployeeController.LeaveController;

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

import com.example.EMS.EmployeeDTO.LeavePolicyDTO;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeavePolicyRepository;
import com.example.EMS.EmployeeService.LeaveService.LeavePolicyService;

@RestController
@RequestMapping("/api/leavePolicy")
public class LeavePolicyController {
	
	private LeavePolicyService leavePolicyService;
	private LeavePolicyRepository leavePolicyRepository;

	public LeavePolicyController(LeavePolicyService leavePolicyService, LeavePolicyRepository leavePolicyRepository) {
		this.leavePolicyService = leavePolicyService;
		this.leavePolicyRepository = leavePolicyRepository;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createPolicy(@RequestBody LeavePolicy request){
		
		 if (request.getTotalDays() == null) {
		        return ResponseEntity.badRequest()
		                .body("Total days cannot be null");
		    }

		    if (request.getYear() == null) {
		        return ResponseEntity.badRequest()
		                .body("Year cannot be null");
		    }

//		    if (request.getMonth() == null) {
//		        return ResponseEntity.badRequest()
//		                .body("Month cannot be null");
//		    }
		    
		    if(request.getLeaveType() == null) {
		    	return ResponseEntity.badRequest()
		    			.body("Please select leave type");
		    }
		    
		    if(request.getDepartment().getName() == null || request.getDepartment().getName().isBlank()) {
		    	return ResponseEntity.badRequest()
		    			.body("Please select leave department");
		    }
		    
		
		    return leavePolicyService.createPolicy(request);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllPolicy(){
		List<LeavePolicy>  lst = leavePolicyRepository.findAll();
		
		if(lst.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave policy list is empty");
		}
		
		List<LeavePolicyDTO> dtoList = new ArrayList<>();
		
		for(LeavePolicy save: lst) {
			LeavePolicyDTO dto = new  LeavePolicyDTO();
			dto.setId(save.getId());
			dto.setTotalDays(save.getTotalDays());
			dto.setYear(save.getYear());
			dto.setMonth(save.getMonth());
			dto.setLeaveType(save.getLeaveType().getName());
			dto.setDepartment(save.getDepartment().getName());
			dto.setCarryForward(save.getCarryForward());
			dto.setEncashment(save.isEncashment());
			dto.setStatus(save.isStatus());
			dto.setLastUpdateDateTime(save.getLastUpdateDateTime());
			
			dtoList.add(dto);
		}
		
		return ResponseEntity.ok(dtoList);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> getAllPolicy(@PathVariable Long id){
		if(id == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter id");
		}
		Optional<LeavePolicy>  policy = leavePolicyRepository.findById(id);
		
		if(policy.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave policy is not found");
		}
		
		return leavePolicyService.deleteById(id);
	}
	
	@PatchMapping("/updateById/{id}")
	public ResponseEntity<?> updateAllPolicy(@PathVariable Long id, @RequestBody LeavePolicy request){
		if(id == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter id");
		}
		Optional<LeavePolicy>  existing = leavePolicyRepository.findById(id);
		
		if(existing.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave policy is not found");
		}
		
		LeavePolicy exist = existing.get();
		
		if(request.getTotalDays() != null) {
			exist.setTotalDays(request.getTotalDays());
		}
		
		if(request.getYear() != null) {
			exist.setYear(request.getYear());
		}
				
		if (request.getCarryForward() != null) {
		    exist.setCarryForward(request.getCarryForward());
		}

		exist.setEncashment(request.isEncashment());

		exist.setStatus(request.isStatus());

		if (request.getLastUpdateDateTime() != null) {
		    exist.setLastUpdateDateTime(request.getLastUpdateDateTime());
		}
		
		return leavePolicyService.updateById(id, exist);
	}
}
