package com.example.EMS.Service.Impl.Role;

import com.example.EMS.Service.Role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.EMS.Entity.Role.Role;
import com.example.EMS.Repository.RoleRepository.RoleRepository;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository repository;

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
