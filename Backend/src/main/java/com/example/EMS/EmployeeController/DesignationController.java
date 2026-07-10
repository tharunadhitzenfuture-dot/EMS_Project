package com.example.EMS.EmployeeController;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.Designation;
import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.EmployeeEntity.Role.Role;
import com.example.EMS.EmployeeRepository.DesignationRepository;
import com.example.EMS.EmployeeRepository.DepartmentRepository.DepartmentRepository;
import com.example.EMS.EmployeeRepository.RoleRepository.RoleRepository;
import com.example.EMS.EmployeeService.DesignationService;

@RestController
@RequestMapping("/api/designation")
public class DesignationController {

	private DesignationService service;
	private DesignationRepository repository;
	private DepartmentRepository departmentRepository;
	private RoleRepository roleRepository;
	
	

	public DesignationController(DesignationService service, DesignationRepository repository,
			DepartmentRepository departmentRepository, RoleRepository roleRepository) {
		
		this.service = service;
		this.repository = repository;
		this.departmentRepository = departmentRepository;
		this.roleRepository = roleRepository;
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Designation request){
		
		if(request.getDepartment() == null || request.getDepartment().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter designation");
		}		
		Optional<Designation> designation =  repository.findByDepartment(request.getDepartment());		
		
		if(designation.isPresent()) {
			return ResponseEntity.badRequest().body("Designation already presented");
		}		
		
		Optional<Role> department = roleRepository.findByRole(request.getDepartment());
		if(department.isEmpty()) {
			return ResponseEntity.badRequest().body("Role not presented with name: "+request.getDepartment());
		}
		
		return service.create(request);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id,
	                                    @RequestBody Designation request) {

	    return service.updateById(id, request);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {

	    return service.getAll();
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

	    return service.getById(id);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

	    return service.deleteById(id);
	}
	
	
	
	
	
	
}
