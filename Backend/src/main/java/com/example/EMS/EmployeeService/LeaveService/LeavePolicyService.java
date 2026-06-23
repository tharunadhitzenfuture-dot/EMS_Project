package com.example.EMS.EmployeeService.LeaveService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveBalanceRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeavePolicyRepository;

@Service
public class LeavePolicyService {
	
	private LeavePolicyRepository leavePolicyRepo;
	private LeaveBalanceRepository leaveBalRepository;
	private EmpRepository empRepo;
	
	
	
	public LeavePolicyService(LeavePolicyRepository leavePolicyRepo, EmpRepository empRepo, LeaveBalanceRepository leaveBalRepository) {
	
		this.leavePolicyRepo = leavePolicyRepo;
		this.empRepo = empRepo;
		this.leaveBalRepository = leaveBalRepository;
	}



	public ResponseEntity<?> createPolicy(LeavePolicy request){
		
		//Optional<LeavePolicy> res = leavePolicyRepo.findByMonthAndYear(request.getMonth(), request.getYear());
		Optional<LeavePolicy> res = leavePolicyRepo.findByYearAndType(request.getYear(), request.getType());
		
		if(res.isPresent()) {
			return ResponseEntity.badRequest().body("Total leave for year: "+ request.getYear()+" and type "+request.getYear()+" already presented");
		}
		
		LeavePolicy save = leavePolicyRepo.save(request);
		
		List<Employee> employee = empRepo.findAll();
		
		for(Employee emp: employee) {
			
			Optional<LeaveBalance> existing = leaveBalRepository.findByEmployeeAndYearAndType(emp,  request.getYear(), request.getType());
			if(existing.isPresent()) {
				LeaveBalance balance = existing.get();
				balance.setTotalDays(request.getTotalDays());
				balance.setRemainingDays(request.getTotalDays());
				balance.setType(request.getType());
				leaveBalRepository.save(balance);
				
			}
			else {
				  LeaveBalance balance = new LeaveBalance();

		            balance.setEmployee(emp);

//		            balance.setMonth(
//		                    request.getMonth()
//		            );

		            balance.setYear(
		                    request.getYear()
		            );

		            balance.setTotalDays(
		                    request.getTotalDays()
		            );
		            
		            balance.setType(request.getType());
		            balance.setRemainingDays(request.getTotalDays());
		            balance.setUsedDays(0);

		            leaveBalRepository.save(balance);
			}
			
		}
		
		
		
		
		return ResponseEntity.ok(save);
		
		
	}

}
