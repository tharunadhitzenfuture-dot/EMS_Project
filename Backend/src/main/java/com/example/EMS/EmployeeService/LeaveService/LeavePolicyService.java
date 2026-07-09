package com.example.EMS.EmployeeService.LeaveService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.DepartmentRepository.DepartmentRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveBalanceRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeavePolicyRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class LeavePolicyService {
	
	private LeavePolicyRepository leavePolicyRepo;
	private LeaveBalanceRepository leaveBalRepository;
	private EmpRepository empRepo;
	private DepartmentRepository departmentRepository;
	private LeaveTypeRepository leaveTypeRepository;

	

	public LeavePolicyService(LeavePolicyRepository leavePolicyRepo, LeaveBalanceRepository leaveBalRepository,
			EmpRepository empRepo, DepartmentRepository departmentRepository, LeaveTypeRepository leaveTypeRepository) {

		this.leavePolicyRepo = leavePolicyRepo;
		this.leaveBalRepository = leaveBalRepository;
		this.empRepo = empRepo;
		this.departmentRepository = departmentRepository;
		this.leaveTypeRepository = leaveTypeRepository;
	}

	public ResponseEntity<?> createPolicy(LeavePolicy request){
		
		Departments department = departmentRepository.findByName(request.getDepartment().getName()).orElseThrow(()-> new RuntimeException("Department not found"));
		request.setDepartment(department);
		//Optional<LeavePolicy> res = leavePolicyRepo.findByMonthAndYear(request.getMonth(), request.getYear());
		Optional<LeaveType> leaveType = leaveTypeRepository.findByName(request.getLeaveType().getName());		
		if(leaveType.isEmpty()) {
			return ResponseEntity.badRequest().body("Leave type not created with name: "+request.getLeaveType().getName());
		}
		request.setLeaveType(leaveType.get());
		Optional<LeavePolicy> res = leavePolicyRepo.findByYearAndLeaveTypeAndDepartment_Name(request.getYear(), leaveType.get(), department.getName());
		
		if(res.isPresent()) {
			return ResponseEntity.badRequest().body("Total leave for year: "+ request.getYear()+" and type "+request.getLeaveType().getName()+" department "+ department.getName()+" already presented");
		}
		
		List<Employee> employee = empRepo.findEmployeesByDepartment(department);
		
		if(employee.size() > 0) {
			for(Employee emp: employee) {
				
				Optional<LeaveBalance> existing = leaveBalRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(emp,  request.getYear(), leaveType.get(), department.getName());
				if(existing.isPresent()) {
					LeaveBalance balance = existing.get();
					balance.setTotalDays(request.getTotalDays());
					balance.setRemainingDays(request.getTotalDays());
					balance.setLeaveType(leaveType.get());
					balance.setDepartment(department);
					leaveBalRepository.save(balance);
					
				}
				else {
					  LeaveBalance balance = new LeaveBalance();

			            balance.setEmployee(emp);

//			            balance.setMonth(
//			                    request.getMonth()
//			            );

			            balance.setYear(
			                    request.getYear()
			            );

			            balance.setTotalDays(
			                    request.getTotalDays()
			            );
			            
			            balance.setLeaveType(leaveType.get());
			            balance.setRemainingDays(request.getTotalDays());
			            balance.setUsedDays(0);
			            balance.setDepartment(department);

			            leaveBalRepository.save(balance);
				}
				
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
		Departments department = departmentRepository.findByName(request.getDepartment().getName()).orElseThrow(()-> new RuntimeException("Department not found"));
		request.setDepartment(department);
		
		Optional<LeaveType> leaveType = leaveTypeRepository.findByName(request.getLeaveType().getName());		
		if(leaveType.isEmpty()) {
			return ResponseEntity.badRequest().body("Leave type not created with name: "+request.getLeaveType().getName());
		}
		request.setLeaveType(leaveType.get());
		
		List<Employee> employee = empRepo.findEmployeesByDepartment(department);
		
		if(employee.isEmpty()) {
			return ResponseEntity.badRequest().body("Employee list for department "+department.getName()+" is empty");
		}
		
		for(Employee emp: employee) {
			
			Optional<LeaveBalance> existing = leaveBalRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(emp,  request.getYear(), leaveType.get(), department.getName());
			if(existing.isPresent()) {
				LeaveBalance balance = existing.get();
				balance.setTotalDays(request.getTotalDays());
				balance.setRemainingDays(request.getTotalDays());
				balance.setLeaveType(request.getLeaveType());
				balance.setDepartment(department);
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
		            
		            balance.setLeaveType(request.getLeaveType());
		            balance.setRemainingDays(request.getTotalDays());
		            balance.setUsedDays(0);
		            balance.setDepartment(department);

		            leaveBalRepository.save(balance);
			}
			
		}
		LeavePolicy save = leavePolicyRepo.save(request);

		return ResponseEntity.ok(save);
		
		
	}
	

}
