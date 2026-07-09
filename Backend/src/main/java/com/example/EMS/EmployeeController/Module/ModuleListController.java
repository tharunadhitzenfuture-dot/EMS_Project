package com.example.EMS.EmployeeController.Module;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.EmployeePermissionDTO;
import com.example.EMS.EmployeeDTO.ModuleListDTO.RolePermissionDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeEntity.Role.Role;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;
import com.example.EMS.EmployeeRepository.RoleRepository.RoleRepository;
import com.example.EMS.EmployeeService.ModuleService.ModuleListService;

@RestController
@RequestMapping("/api/moduleList")
public class ModuleListController {
	
	private ModuleListRepository moduleListrepository;
	private ModuleListService moduleListservice;
	private RoleRepository roleRepository;
	private ModuleRepository moduleRepository;
	private EmpRepository employeeRepository;
	
	

	public ModuleListController(ModuleListRepository moduleListrepository, ModuleListService moduleListservice,
			RoleRepository roleRepository, ModuleRepository moduleRepository, EmpRepository employeeRepository) {
		this.moduleListrepository = moduleListrepository;
		this.moduleListservice = moduleListservice;
		this.roleRepository = roleRepository;
		this.moduleRepository = moduleRepository;
		this.employeeRepository = employeeRepository;
	}


	@PostMapping("/rolePermission")
	public ResponseEntity<?> changePermissionRole(@RequestBody RolePermissionDTO dto){
		
		if(dto.getRole() == null || dto.getRole().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter role name");
		}
		Optional<Role> role = roleRepository.findByRole(dto.getRole());
		
		
		
		if(role.isEmpty()) {
			return ResponseEntity.badRequest().body("Please valid role name");
		}
		
		Long roleId  = role.get().getId();
		Long moduleId = dto.getModuleId();
		
		if(dto.getRole().equalsIgnoreCase("ADMIN") || roleId == 1) {
			return ResponseEntity.badRequest().body("ADMIN permission menu cannot be modified");
		}
		
		Optional<ModuleEntity> entity = moduleRepository.findById(moduleId);
		
		if(entity.isEmpty()) {
			return ResponseEntity.badRequest().body("No module with id: "+moduleId);
		}
		
		return moduleListservice.roleWisePermission(dto, roleId, moduleId);
		
	}
	
	
	@PostMapping("/employeePermission")
	public ResponseEntity<?> changeEmployeePermission(@RequestBody EmployeePermissionDTO dto){
		
		if(dto.getEmpId() == null || dto.getEmpId().isBlank()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter employee id");
		}
		
		Optional<Employee> emp =employeeRepository.findByEmployeeId(dto.getEmpId());
		Employee employee = emp.get();
		
		Long id = employee.getUser().getId();
		if(id == null) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with employee id: "+dto.getEmpId());
		}
		
		if(id == 1) {
			return ResponseEntity.badRequest().body("ADMIN permission menu cannot be modified");	
		}
		
		if(dto.getModuleId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter module id");
		}
		
		return moduleListservice.employeePermission(dto, id);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		List<ModuleList> list = moduleListrepository.findAll();
		
		if(list.size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module list is empty");
		}
		
		return ResponseEntity.ok(list);
		
	}
	
	
	
	

}
