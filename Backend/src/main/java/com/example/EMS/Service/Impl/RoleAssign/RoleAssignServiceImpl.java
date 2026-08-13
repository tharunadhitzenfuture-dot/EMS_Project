package com.example.EMS.Service.Impl.RoleAssign;

import java.util.List;

import com.example.EMS.Service.RoleAssign.RoleAssignService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.RolesAssign;
import com.example.EMS.Repository.RoleAssignRepository;

@Service
@AllArgsConstructor
public class RoleAssignServiceImpl implements RoleAssignService {

	private final RoleAssignRepository repository;

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
