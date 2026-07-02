package com.example.EMS.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	
	
}
