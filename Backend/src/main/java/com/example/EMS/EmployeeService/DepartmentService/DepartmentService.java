package com.example.EMS.EmployeeService.DepartmentService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.EmployeeRepository.DepartmentRepository.DepartmentRepository;

@Service
public class DepartmentService {

	private DepartmentRepository deptRepository;
	
	public DepartmentService(DepartmentRepository deptRepository) {
		this.deptRepository = deptRepository;
	}
	
	
	public ResponseEntity<?> create(List<Departments> request){
		
		List<Departments> res = deptRepository.saveAll(request);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> update(Long id, Departments request) {

        Departments existing = deptRepository.findById(id).get();

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        Departments updated = deptRepository.save(existing);

        return ResponseEntity.ok(updated);
    }
	
	public ResponseEntity<?> delete(Long id) {

        deptRepository.deleteById(id);

        return ResponseEntity.ok("Department deleted successfully");
    }
	
}
