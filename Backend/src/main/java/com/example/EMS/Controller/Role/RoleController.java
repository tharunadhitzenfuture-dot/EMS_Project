package com.example.EMS.Controller.Role;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.EMS.Entity.Role.Role;
import com.example.EMS.Repository.RoleRepository.RoleRepository;
import com.example.EMS.Service.Role.RoleService;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleRepository repository;
	private final RoleService service;

	@PostMapping("/create")
	public ResponseEntity<?> createRole(@RequestBody Role request){
		
		if(request.getRole() == null || request.getRole().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter role name");
		}
		
		Optional<Role>  res =repository.findByRole(request.getRole());
		
		if(res.isPresent()) {
			return ResponseEntity.badRequest().body("Role already created with name: "+request.getRole());
		}
		return service.createRole(request);
	
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		
		List<Role> lst = repository.findAll();
		return ResponseEntity.ok(lst);
		
	
	}

	@GetMapping("/getByRole")
	public ResponseEntity<?> getByRole(@RequestParam String role){
		Optional<Role>  res = repository.findByRole(role);
		
		if(res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found.");
		}
		
		return ResponseEntity.ok(res.get());
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteByRole(@PathVariable Long id){
		Optional<Role>  res = repository.findById(id);
		
		if(res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found.");
		}
		
		if(id == 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot delete admin");
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("Role deleted with id: "+id);
	}
	
	@PutMapping("/updateById/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody Role request){
		if(request.getRole() == null || request.getRole().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter role name");
		}
		Optional<Role> exist = repository.findById(id);
		
		if(id == 1 || request.getRole().equalsIgnoreCase("ADMIN")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot update admin");
		}
		
		if(exist.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found with id: "+id);
		}
		
		return service.update(exist.get(), request);
		
		
	}
	
	

}
