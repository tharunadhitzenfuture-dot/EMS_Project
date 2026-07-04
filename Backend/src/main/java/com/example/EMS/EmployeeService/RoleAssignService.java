package com.example.EMS.EmployeeService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.RolesAssign;
import com.example.EMS.EmployeeRepository.RoleAssignRepository;



@Service
public class RoleAssignService {

	private RoleAssignRepository repository;

	public RoleAssignService(RoleAssignRepository repository) {
		this.repository = repository;
	}
	
	
	public ResponseEntity<?> createRole(RolesAssign request){
		
		RolesAssign res= repository.save(request);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> update(RolesAssign exist, RolesAssign request){
		
		exist.setRole(request.getRole());
		exist.setParent_Role(request.getParent_Role());
		exist.setDescription(request.getDescription());
		
		RolesAssign res =  repository.save(exist);
		return ResponseEntity.ok(res);
		
	}
	
	public List<Employee> searchEmployee(String department, String name) {
	    return repository.searchEmployee(department, name);
	}
	
}
