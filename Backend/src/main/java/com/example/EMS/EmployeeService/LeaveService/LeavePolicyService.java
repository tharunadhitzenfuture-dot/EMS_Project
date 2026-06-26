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

import jakarta.transaction.Transactional;

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
		Optional<LeavePolicy> res = leavePolicyRepo.findByYearAndTypeAndDepartment(request.getYear(), request.getType(), request.getDepartment());
		
		if(res.isPresent()) {
			return ResponseEntity.badRequest().body("Total leave for year: "+ request.getYear()+" and type "+request.getType()+" department "+ request.getDepartment()+" already presented");
		}
		
		List<Employee> employee = empRepo.findByProfessional_detailsProfessional_department(request.getDepartment().toString());
		
		if(employee.isEmpty()) {
			return ResponseEntity.badRequest().body("Employee list for department "+request.getDepartment()+" is empty");
		}

		for(Employee emp: employee) {
			
			Optional<LeaveBalance> existing = leaveBalRepository.findByEmployeeAndYearAndTypeAndDepartment(emp,  request.getYear(), request.getType(), request.getDepartment());
			if(existing.isPresent()) {
				LeaveBalance balance = existing.get();
				balance.setTotalDays(request.getTotalDays());
				balance.setRemainingDays(request.getTotalDays());
				balance.setType(request.getType());
				balance.setDepartment(request.getDepartment());
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
		            balance.setDepartment(request.getDepartment());

		            leaveBalRepository.save(balance);
			}
			
		}
		LeavePolicy save = leavePolicyRepo.save(request);
		
		
		
		
		return ResponseEntity.ok(save);
		
		
	}
	
	@Transactional
	public ResponseEntity<?> deleteById(Long id){
		leavePolicyRepo.deleteById(id);
		return ResponseEntity.ok("Leave policy deleted");
	}
	
	
		public ResponseEntity<?> updateById(Long id, LeavePolicy request){
		
		//Optional<LeavePolicy> res = leavePolicyRepo.findByMonthAndYear(request.getMonth(), request.getYear());
		List<Employee> employee = empRepo.findByProfessional_detailsProfessional_department(request.getDepartment().toString());
		
		if(employee.isEmpty()) {
			return ResponseEntity.badRequest().body("Employee list for department "+request.getDepartment()+" is empty");
		}

		for(Employee emp: employee) {
			
			Optional<LeaveBalance> existing = leaveBalRepository.findByEmployeeAndYearAndTypeAndDepartment(emp,  request.getYear(), request.getType(), request.getDepartment());
			if(existing.isPresent()) {
				LeaveBalance balance = existing.get();
				balance.setTotalDays(request.getTotalDays());
				balance.setRemainingDays(request.getTotalDays());
				balance.setType(request.getType());
				balance.setDepartment(request.getDepartment());
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
		            balance.setDepartment(request.getDepartment());

		            leaveBalRepository.save(balance);
			}
			
		}
		LeavePolicy save = leavePolicyRepo.save(request);

		return ResponseEntity.ok(save);
		
		
	}
	

}
