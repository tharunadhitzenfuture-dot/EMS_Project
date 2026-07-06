package com.example.EMS.EmployeeController.Module;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.ModuleListDTO.RolePermissionDTO;
import com.example.EMS.EmployeeEntity.Role.Role;
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
	
	
	
	public ModuleListController(ModuleListRepository moduleListrepository, ModuleListService moduleListservice,
			RoleRepository roleRepository, ModuleRepository moduleRepository) {
		this.moduleListrepository = moduleListrepository;
		this.moduleListservice = moduleListservice;
		this.roleRepository = roleRepository;
		this.moduleRepository = moduleRepository;
	}



//	@PostMapping("/permission")
//	public ResponseEntity<?> changePermissionRole(@RequestBody RolePermissionDTO dto){
//		
//		if(dto.getRole() == null || dto.getRole().isBlank()) {
//			return ResponseEntity.badRequest().body("Please enter role name");
//		}
//		Optional<Role> role = roleRepository.findByRole(dto.getRole());
//		if(role.isEmpty()) {
//			return ResponseEntity.badRequest().body("Please valid role name");
//		}
//		
//		
//		
//	}
	
	
	
	
	

}
