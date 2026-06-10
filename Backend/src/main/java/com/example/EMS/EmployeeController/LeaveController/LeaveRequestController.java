package com.example.EMS.EmployeeController.LeaveController;

import java.time.LocalDate;
import java.util.List;

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

import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveRequestRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveRequestService;
import com.example.EMS.enums.LeaveType;

@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
	
	private final LeaveRequestService requestService;
	private final LeaveRequestRepository requestRepository;
	private final EmpRepository empRepo;
	

	


	public LeaveRequestController(LeaveRequestService requestService, LeaveRequestRepository requestRepository,
			EmpRepository empRepo) {
		this.requestService = requestService;
		this.requestRepository = requestRepository;
		this.empRepo = empRepo;
	}

	@PostMapping("/apply/{empId}")
	public ResponseEntity<?> applyLeave(@PathVariable String empId, @RequestBody LeaveRequest request){
		if(request.getStartDate() == null || request.getEndDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide start date and end date");
		}
		return requestService.applyLeave(empId, request);

	}
	
	@PostMapping("/review/{empId}/{leaveId}")
	 public ResponseEntity<?> reviewLeave(@PathVariable String empId,@PathVariable Long leaveId,@RequestBody ReviewLeaveDto dto) {
		
		if(empId == null || leaveId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide employee id and leave id");
		}
		
		LeaveRequest req = getLeaveById(leaveId);
		
		if(req.getLeaveType() == LeaveType.FULL_DAY) {
			return requestService.reviewLeave(empId, leaveId, dto);
		}
		else if(req.getLeaveType() == LeaveType.HALF_DAY) {
			return requestService.reviewHalfDayLeave(empId, leaveId, dto);
		}
		
		return ResponseEntity.badRequest().body("Please mention correct leave type FULL_DAY, HALF_DAY, PERMISSION");
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllLeaves() {
		List<LeaveRequest> lst = requestService.getAllLeaves();
		if(lst.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave requests list empty");
		}
	    return ResponseEntity.ok(lst);
	}
	
	
	@GetMapping("/getById/{id}")
	private LeaveRequest getLeaveById(@PathVariable Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
    }
	
	 @GetMapping("/getListLeaveById/{empId}/{date}")
	 public ResponseEntity<?> getListPermission(@PathVariable String empId, @PathVariable LocalDate date) {
		
		 if(empId == null) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter employee id");
		 }
		 Long id = empRepo.findIdByEmployeeId(empId);
		List<LeaveRequest> leave = requestRepository.findLeavesContainingDate(id, date);
		if(leave == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave not found with id: "+empId+" Date "+date);
		}
		return ResponseEntity.ok(leave);
	}
	
	@PutMapping("/update/{empId}/{leaveId}")
	public ResponseEntity<?> updateLeave(
	        @PathVariable String empId,
	        @PathVariable Long leaveId,
	        @RequestBody LeaveRequest request) {

	    return requestService.updateLeave(
	            empId,
	            leaveId,
	            request);
	}
	
	@DeleteMapping("/delete/{empId}/{leaveId}")
	public ResponseEntity<?> deleteLeave(
	        @PathVariable String empId,
	        @PathVariable Long leaveId) {

	    return requestService.deleteLeave(
	            empId,
	            leaveId);
	}
}
