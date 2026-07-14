package com.example.EMS.EmployeeController.LeaveController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.LeaveBalanceDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveBalanceRepository;

@RestController
@RequestMapping("/api/leaveBalance")
public class LeaveBalanceController {

	private LeaveBalanceRepository leaveBalanceRepository;
	private EmpRepository empRepo;

	
	
	public LeaveBalanceController(LeaveBalanceRepository leaveBalanceRepository, EmpRepository empRepo) {
		this.leaveBalanceRepository = leaveBalanceRepository;
		this.empRepo = empRepo;
	}



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
			  dto.setTotalDays(dto.getTotalDays());
			  dto.setRemainingDays(bal.getRemainingDays());
			  dto.setYear(bal.getYear());
			  dto.setUsedDays(bal.getUsedDays());
			  dto.setLeaveType(bal.getLeaveType().getId());
			  dto.setDepartment(bal.getDepartment().getId());
			  
			  lstDto.add(dto);
			  
		  }

		  return ResponseEntity.ok(lstDto);
		  
	}
	
	
	
	
}
