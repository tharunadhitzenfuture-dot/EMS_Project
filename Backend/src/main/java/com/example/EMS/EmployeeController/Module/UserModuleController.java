package com.example.EMS.EmployeeController.Module;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.Module.UserModule;
import com.example.EMS.EmployeeRepository.ModuleRepository.UserModuleRepository;

@RestController
@RequestMapping("/api/roleModule")
public class UserModuleController {

	private final UserModuleRepository userModuleRepository;

	public UserModuleController(UserModuleRepository userModuleRepository) {
		
		this.userModuleRepository = userModuleRepository;
	}
	
	
	@GetMapping("/getRole/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		List<UserModule>  lst = userModuleRepository.findAllByRole_Id(id);
		
		return ResponseEntity.ok(lst);
		
	}
	
	
}
