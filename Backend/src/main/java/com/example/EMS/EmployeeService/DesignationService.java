package com.example.EMS.EmployeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Designation;
import com.example.EMS.EmployeeRepository.DesignationRepository;

@Service
public class DesignationService {


	private DesignationRepository repository;
	
	public DesignationService(DesignationRepository repository) {
		this.repository = repository;
	}
	
	public ResponseEntity<?> create(Designation designation){
		
		Designation res = repository.save(designation);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> updateById(Long id, Designation request) {

	    Designation existing = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Designation not found"));

	    existing.setDepartment(request.getDepartment());
	    existing.setDesignation(request.getDesignation());
	    existing.setDescription(request.getDescription());

	    Designation updated = repository.save(existing);

	    return ResponseEntity.ok(updated);
	}

	public ResponseEntity<?> getAll() {

	    return ResponseEntity.ok(repository.findAll());
	}

	public ResponseEntity<?> getById(Long id) {

	    Designation designation = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Designation not found"));

	    return ResponseEntity.ok(designation);
	}

	public ResponseEntity<?> deleteById(Long id) {

	    Designation designation = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Designation not found"));

	    repository.delete(designation);

	    return ResponseEntity.ok("Designation deleted successfully");
	}
	
	
	
	
	
}
