package com.example.EMS.Controller.Designation;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.Entity.Designation;
import com.example.EMS.Entity.Departments.Departments;
import com.example.EMS.Repository.DesignationRepository;
import com.example.EMS.Repository.DepartmentRepository.DepartmentRepository;
import com.example.EMS.Repository.RoleRepository.RoleRepository;
import com.example.EMS.Service.Designation.DesignationService;

@RestController
@RequestMapping("/api/designation")
@RequiredArgsConstructor
public class DesignationController {

	private final DesignationService service;
	private final DesignationRepository repository;
	private final DepartmentRepository departmentRepository;
	private final RoleRepository roleRepository;


	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Designation request){
		
		if(request.getDepartment() == null || request.getDepartment().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter designation");
		}		
		Optional<Designation> designation =  repository.findByDepartment(request.getDepartment());		
		
		if(designation.isPresent()) {
			return ResponseEntity.badRequest().body("Designation already presented");
		}		
		
		Optional<Departments> department = departmentRepository.findByName(request.getDepartment());
		if(department.isEmpty()) {
			return ResponseEntity.badRequest().body("Department not presented with name: "+request.getDepartment());
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
