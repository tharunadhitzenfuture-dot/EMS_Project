package com.example.EMS.Controller.Module;


import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.EMS.Entity.Module.ModuleEntity;
import com.example.EMS.Repository.ModuleRepository.ModuleRepository;
import com.example.EMS.Service.ModuleService.ModuleService;

@RestController
@RequestMapping("/api/module")
@RequiredArgsConstructor
public class ModuleController {
	
	private final ModuleRepository repository;
	private final ModuleService service;


	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ModuleEntity request){
		if(request.getModuleName() == null || request.getModuleName().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter module name");
		}

		Optional<ModuleEntity> entity = repository.findByModuleName(request.getModuleName());
		if(entity.isPresent()){
			return ResponseEntity.badRequest().body("Module already presented with name: "+request.getModuleName());
		}
		

		
		return service.create(request);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		
		List<ModuleEntity> list = repository.findAll();
		
		if(list.size() <1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No module presented");
		}
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		
		Optional<ModuleEntity> module = repository.findById(id);
		
		if(module.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No module presented with id: "+id);
		}
		
		repository.deleteById(id);
		return ResponseEntity.ok("Module deleted with id: "+id);
		
	}
	

}
