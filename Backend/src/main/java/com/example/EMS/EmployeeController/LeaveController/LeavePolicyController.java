package com.example.EMS.EmployeeController.LeaveController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.EmployeeService.LeaveService.LeavePolicyService;

@RestController
@RequestMapping("/api/leavePolicy")
public class LeavePolicyController {
	
	private LeavePolicyService leavePolicyService;
	

	public LeavePolicyController(LeavePolicyService leavePolicyService) {
	
		this.leavePolicyService = leavePolicyService;
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

		    if (request.getMonth() == null) {
		        return ResponseEntity.badRequest()
		                .body("Month cannot be null");
		    }
		
		    return leavePolicyService.createPolicy(request);
	}
}
