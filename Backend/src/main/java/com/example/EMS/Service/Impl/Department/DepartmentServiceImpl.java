package com.example.EMS.Service.Impl.Department;

import java.util.List;

import com.example.EMS.Service.Department.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.Entity.Departments.Departments;
import com.example.EMS.Repository.DepartmentRepository.DepartmentRepository;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository deptRepository;

	
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
