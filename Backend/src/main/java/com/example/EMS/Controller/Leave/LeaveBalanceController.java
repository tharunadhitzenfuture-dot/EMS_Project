package com.example.EMS.Controller.Leave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.LeaveBalanceDTO;
import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.User;
import com.example.EMS.Entity.LeaveEntity.LeaveBalance;
import com.example.EMS.Repository.EmpRepository;
import com.example.EMS.Repository.LeaveRepository.LeaveBalanceRepository;

@RestController
@RequestMapping("/api/leaveBalance")
@RequiredArgsConstructor
public class LeaveBalanceController {

	private final LeaveBalanceRepository leaveBalanceRepository;
	private final EmpRepository empRepo;


	@GetMapping("/getBalance")
	public ResponseEntity<?> getLeaveBalance(){
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			User user = (User) authentication.getPrincipal();
			
			Optional<Employee> empUser = empRepo.findByUser(user);
			
			if(empUser.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
			}
			
	      String empId = empUser.get().getEmployeeId();
		  Long id = empRepo.findIdByEmployeeId(empId);
		  
		  List<LeaveBalance> balance = leaveBalanceRepository.findByEmployeeId(id);
		  
		  if(balance.size() == 0) {
			  return ResponseEntity.badRequest().body("Leave policy not created for this employee");
		  }
		  
		  List<LeaveBalanceDTO> lstDto = new ArrayList<>();
		  for(LeaveBalance bal : balance) {
			  LeaveBalanceDTO dto = new LeaveBalanceDTO();
			  dto.setId(bal.getId());
			  dto.setEmployeeId(bal.getEmployee_Id());
			  dto.setTotalDays(bal.getTotalDays());
			  dto.setRemainingDays(bal.getRemainingDays());
			  dto.setYear(bal.getYear());
			  dto.setUsedDays(bal.getUsedDays());
			  dto.setLeaveType(bal.getLeaveTypeName());
			  dto.setDepartment(bal.getDepartmentName());
			  
			  lstDto.add(dto);
			  
		  }

		  return ResponseEntity.ok(lstDto);
		  
	}
	
	
	
	
}
