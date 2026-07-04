package com.example.EMS.EmployeeController;

import java.util.List;
import java.util.Optional;

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

import com.example.EMS.EmployeeEntity.RolesAssign;
import com.example.EMS.EmployeeRepository.RoleAssignRepository;
import com.example.EMS.EmployeeService.RoleAssignService;

@RestController
@RequestMapping("/api/role")
public class RoleAssignController {
	
	private RoleAssignRepository repository;
	private RoleAssignService service;
	
	public RoleAssignController(RoleAssignRepository repository, RoleAssignService service) {
		this.repository = repository;
		this.service = service;
	}
	
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createRole(@RequestBody RolesAssign request){
		
		if(request.getRole() == null || request.getRole().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter role name");
		}
		
		Optional<RolesAssign>  res =repository.findByRole(request.getRole());
		
		if(res.isPresent()) {
			return ResponseEntity.badRequest().body("Role already created with name: "+request.getRole());
		}
		return service.createRole(request);
	
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		
		List<RolesAssign> lst = repository.findAll();
		return ResponseEntity.ok(lst);
		
	
	}

	@GetMapping("/getByRole")
	public ResponseEntity<?> getByRole(@RequestParam String role){
		Optional<RolesAssign>  res = repository.findByRole(role);
		
		if(res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found.");
		}
		
		return ResponseEntity.ok(res.get());
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> getByRole(@PathVariable Long id){
		Optional<RolesAssign>  res = repository.findById(id);
		
		if(res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found.");
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("Role deleted with id: "+id);
	}
	
	@PutMapping("/updateById/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody RolesAssign request){
		if(request.getRole() == null || request.getRole().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter role name");
		}
		Optional<RolesAssign> exist = repository.findById(id);
		
		if(exist.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Role is not found with id: "+id);
		}
		
		return service.update(exist.get(), request);
		
		
	}

}
