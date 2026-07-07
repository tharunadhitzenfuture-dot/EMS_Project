package com.example.EMS.EmployeeController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeDTO.CompanyDetailDTO;
import com.example.EMS.EmployeeEntity.CompanySettings;
import com.example.EMS.EmployeeRepository.CompanySettingsRepository;
import com.example.EMS.EmployeeService.CompanySettingsService;

@RestController
@RequestMapping("/api/companySetting")
public class CompanySettingsController {
	
	private final CompanySettingsRepository repository;
	private final CompanySettingsService service;
	
	

	public CompanySettingsController(CompanySettingsRepository repository, CompanySettingsService service) {
		this.repository = repository;
		this.service = service;
	}



	@PostMapping("/create")
	public ResponseEntity<?> createCompany(@RequestPart(value="request",required=true) CompanySettings request, 
											@RequestPart(value="logo", required=false) MultipartFile logo){
		
		if(request.getCompanyName() == null || request.getCompanyName().isBlank()) {
			return ResponseEntity.badRequest().body("Please enter company name");
		}
		
		if (logo != null && !logo.isEmpty()) {

		    String fileType = logo.getOriginalFilename();

		    boolean isPdfExtension =
	                fileType != null &&
	                fileType.toLowerCase().endsWith(".jpeg") ||
	                fileType.toLowerCase().endsWith(".jpg") ||
	                fileType.toLowerCase().endsWith(".png") ;
		 

		    if (!isPdfExtension) {

		        return ResponseEntity.badRequest()
		                .body("Only JPG, JPEG, PNG images are allowed for profile");
		    }
		}
		
		if(repository.findByCompanyName(request.getCompanyName()).isPresent()) {
			return ResponseEntity.badRequest().body("Company detailed already entered for:"+request.getCompanyName());
		}
		
		return service.createCompanyDetails(request, logo);
	}
	
	
	@PatchMapping("updateDetails/{id}")
	public ResponseEntity<?> updateDetails(@PathVariable Long id,
			@RequestPart(value="request",required=true) CompanySettings request, 
			@RequestPart(value="logo", required=false) MultipartFile logo){
		
		
		if (logo != null && !logo.isEmpty()) {

		    String fileType = logo.getOriginalFilename();

		    boolean isPdfExtension =
	                fileType != null &&
	                fileType.toLowerCase().endsWith(".jpeg") ||
	                fileType.toLowerCase().endsWith(".jpg") ||
	                fileType.toLowerCase().endsWith(".png") ;
		 

		    if (!isPdfExtension) {

		        return ResponseEntity.badRequest()
		                .body("Only JPG, JPEG, PNG images are allowed for profile");
		    }
		}
		
		return service.updateDetails(id, request, logo);

	}
	
	@GetMapping("/getAll")
    public ResponseEntity<List<CompanySettings>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
	
	@GetMapping("/getAllDetails")
    public ResponseEntity<List<CompanyDetailDTO>> getAllDetails() { 
        return ResponseEntity.ok(service.getAllDetail());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CompanySettings> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
