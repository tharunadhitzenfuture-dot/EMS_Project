package com.example.EMS.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.EMS.EmployeeEntity.Role.Role;
import com.example.EMS.EmployeeRepository.RoleRepository.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository repository;

	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}
	
	public ResponseEntity<?> createRole(Role request){
		
		Role res= repository.save(request);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> update(Role exist, Role request){
		
		exist.setRole(request.getRole());
		exist.setParent_Role(request.getParent_Role());
		exist.setDescription(request.getDescription());
		
		Role res =  repository.save(exist);
		return ResponseEntity.ok(res);
		
	}
	

}
