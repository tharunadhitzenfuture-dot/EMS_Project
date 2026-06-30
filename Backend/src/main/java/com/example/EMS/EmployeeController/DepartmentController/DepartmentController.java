package com.example.EMS.EmployeeController.DepartmentController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.EmployeeRepository.DepartmentRepository.DepartmentRepository;
import com.example.EMS.EmployeeService.DepartmentService.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	private DepartmentService deptService;
	private DepartmentRepository deptRepository;
	public DepartmentController(DepartmentService deptService, DepartmentRepository deptRepository) {

		this.deptService = deptService;
		this.deptRepository = deptRepository;
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody List<Departments> departments) {

    Map<String, String> result = new LinkedHashMap<>();
    List<Departments> departmentsToSave = new ArrayList<>();

    for (Departments dept : departments) {

        if (dept.getName() == null || dept.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Please enter department name");
        }

        String name = dept.getName().trim().toUpperCase();
        dept.setName(name);

        if (deptRepository.findByName(name).isPresent()) {
            result.put(name, "Already exists");
        } else {
            departmentsToSave.add(dept);
            result.put(name, "Added");
        }
    }

    if (!departmentsToSave.isEmpty()) {
        deptService.create(departmentsToSave);
    }

    return ResponseEntity.ok(result);
}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllDepartment(){
		List<Departments> lst =  deptRepository.findAll();
		if(lst.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department list empty");
		}
		
		return ResponseEntity.ok(lst);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
	                                @RequestBody Departments dept) {

	    if (dept.getName() == null || dept.getName().isBlank()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Please enter department name");
	    }

	    Optional<Departments> existingDept = deptRepository.findById(id);

	    if (existingDept.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Department not found");
	    }


	    return deptService.update(id, dept);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

	    Optional<Departments> dept = deptRepository.findById(id);

	    if (dept.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Department not found");
	    }

	    return deptService.delete(id);
	}
	
	
}
