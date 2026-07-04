package com.example.EMS.EmployeeController;

import java.util.ArrayList;
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

import com.example.EMS.EmployeeDTO.ApproverResponseDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.RolesAssign;
import com.example.EMS.EmployeeRepository.RoleAssignRepository;
import com.example.EMS.EmployeeService.RoleAssignService;

@RestController
@RequestMapping("/api/roleAssign")
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role is not found.");
		}
		
		return ResponseEntity.ok(res.get());
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> getByRole(@PathVariable Long id){
		Optional<RolesAssign>  res = repository.findById(id);
		
		if(res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role is not found.");
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role is not found with id: "+id);
		}
		
		return service.update(exist.get(), request);
		
		
	}
	
	
	@GetMapping("/search")
	public List<ApproverResponseDTO> searchEmployee(
	        @RequestParam String department,
	        @RequestParam String name) {

	    List<Employee> employees = service.searchEmployee(department, name);

	    List<ApproverResponseDTO> res = new ArrayList<>();

	    for (Employee emp : employees) {
	        ApproverResponseDTO dto = new ApproverResponseDTO();
	        dto.setEmpId(emp.getEmployeeId());
	        dto.setEmail(emp.getEmail());
	        dto.setName(emp.getFirst_name() + " " + emp.getLast_name());
	        res.add(dto);
	    }

	    return res;
	}

}
