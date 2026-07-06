package com.example.EMS.EmployeeController.Module;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;
import com.example.EMS.EmployeeService.ModuleService.ModuleService;

@RestController
@RequestMapping("/api/module")
public class ModuleController {
	
	private ModuleRepository repository;
	private ModuleService service;
	
	public ModuleController(ModuleRepository repository, ModuleService service) {
		this.repository = repository;
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ModuleEntity request){
		if(request.getModuleName() == null || request.getModuleName().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter module name");
		}

		Optional<ModuleEntity> entity = repository.findByModuleName(request.getModuleName());
		if(entity.isPresent()){
			return ResponseEntity.badRequest().body("Module already presented with name: "+request.getModuleName());
		}
		
		ModuleEntity res = repository.save(request);
		return ResponseEntity.ok(res);
		
	}
	
	
	

}
