package com.example.EMS.EmployeeController.LeaveController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.LeaveRequestDTO;
import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveRequestRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveRequestService;

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
	
	@PostMapping("/empLeaveApply")
	public ResponseEntity<?> applyEmpLeave(@RequestBody LeaveRequest request){
		if(request.getStartDate() == null || request.getEndDate() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide start date and end date");
		}
		return requestService.applyEmpLeave( request);

	}
	
	@PostMapping("/reviewLeave/{leaveId}")
	 public ResponseEntity<?> reviewLeave(@PathVariable Long leaveId,@RequestBody ReviewLeaveDto dto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		Optional<Employee> empUser = empRepo.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
		}
		
		String empId = empUser.get().getEmployeeId();
		
		if(empId == null || leaveId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee id not found for user");
		}
		
		LeaveRequest req = getLeaveById(leaveId);
		
//		if(req.getLeaveTime() == LeaveType.FULL_DAY) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveTime() == LeaveType.HALF_DAY) {
//			return requestService.reviewHalfDayLeave(empId, leaveId, dto);
//		}

//		if(req.getLeaveType() == LeaveType.EARNED_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveType() == LeaveType.CASUAL_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveType() == LeaveType.SICK_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
		
		return requestService.handleReviewLeave(empId, leaveId, dto);
		
//		return ResponseEntity.badRequest().body("Please mention correct leave type EARNED_LEAVE , CASUAL_LEAVE, SICK_LEAVE");
	}
	
	@PostMapping("/review/{empId}/{leaveId}")
	 public ResponseEntity<?> reviewLeave(@PathVariable String empId,@PathVariable Long leaveId,@RequestBody ReviewLeaveDto dto) {
		if(empId == null || leaveId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide employee id and leave id");
		}
		
		LeaveRequest req = getLeaveById(leaveId);
		
//		if(req.getLeaveTime() == LeaveType.FULL_DAY) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveTime()				
//				== LeaveType.HALF_DAY) {
//			return requestService.reviewHalfDayLeave(empId, leaveId, dto);
//		}
//		if(req.getLeaveType() == LeaveType.EARNED_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveType() == LeaveType.CASUAL_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
//		else if(req.getLeaveType() == LeaveType.SICK_LEAVE) {
//			return requestService.reviewLeave(empId, leaveId, dto);
//		}
		
		return requestService.handleReviewLeave(empId, leaveId, dto);
		
	//	return ResponseEntity.badRequest().body("Please mention correct leave type EARNED_LEAVE , CASUAL_LEAVE, SICK_LEAVE");
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllLeaves() {
		List<LeaveRequest> lst = requestService.getAllLeaves();
		if(lst.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave requests list empty");
		}
		
		List<LeaveRequestDTO> lstDTO = new ArrayList<>();
		
		for(LeaveRequest req: lst) {
		       LeaveRequestDTO dto = new LeaveRequestDTO();
		        
		        dto.setId(req.getId());
		        dto.setEmpId(req.getEmployee_Id());
		        dto.setStartDate(req.getStartDate());
		        dto.setEndDate(req.getEndDate());
   		        dto.setTotalDays(req.getTotalDays());
		        dto.setLeaveTime(req.getLeaveTime());
		        dto.setLeaveType(req.getLeaveType().getName());
		        dto.setDepartment(req.getDepartment());
		        dto.setApproverEmail1(req.getApproverEmail1());
		        dto.setApproverEmail2(req.getApproverEmail2());
		        dto.setStatus(req.getStatus());
		        dto.setReason(req.getReason());
		        if(req.getReviewedBy() != null) {
		        	 dto.setReviewedBy(req.getReviewedBy().getFirst_name()+" "+req.getReviewedBy().getLast_name());
		        }		       
		        dto.setReviewedAt(req.getReviewedAt());
		        dto.setCreatedAt(req.getCreatedAt());
		        dto.setHrRemarks(req.getHrRemarks());
		        dto.setLeavePaid(req.isLeavePaid());
		        lstDTO.add(dto);
		}
	    return ResponseEntity.ok(lstDTO);
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
