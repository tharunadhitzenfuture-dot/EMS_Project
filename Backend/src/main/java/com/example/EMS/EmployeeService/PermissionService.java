package com.example.EMS.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeException.BadRequestException;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;

@Service
public class PermissionService {
	
	private final EmpRepository empRepository;
	private final PermissionRepository permissionRepo;
	
	
	

	public PermissionService(EmpRepository empRepository, PermissionRepository permissionRepo) {
		
		this.empRepository = empRepository;
		this.permissionRepo = permissionRepo;
	}


	public ResponseEntity<?> applyPermission(String empId, Permission permission){
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		

		 
        if (permission.getEndDate().isBefore(permission.getStartDate()))
            throw new BadRequestException("End date must be on or after start date");
        
        
        permission.setEmployee(emp);
        Permission res = permissionRepo.save(permission);
        return ResponseEntity.ok(res);
		
        
		
	}
	
	
	private Employee getUserByEmployeeId(Long empId) {
        return empRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + empId));
    }


}
