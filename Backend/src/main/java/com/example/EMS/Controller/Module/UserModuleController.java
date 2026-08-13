package com.example.EMS.Controller.Module;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.Entity.Module.UserModule;
import com.example.EMS.Repository.ModuleRepository.UserModuleRepository;

@RestController
@RequestMapping("/api/roleModule")
@RequiredArgsConstructor
public class UserModuleController {

	private final UserModuleRepository userModuleRepository;

	@GetMapping("/getRole/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		List<UserModule>  lst = userModuleRepository.findAllByRole_Id(id);
		
		return ResponseEntity.ok(lst);
		
	}
	
	
}
