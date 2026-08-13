package com.example.EMS.Service.Impl.CompanySettings;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.EMS.Service.CompanySettings.CompanySettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeDTO.CompanyDetailDTO;
import com.example.EMS.Entity.CompanySettings;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Repository.CompanySettingsRepository;

@Service
@AllArgsConstructor
public class CompanySettingsServiceImpl implements CompanySettingsService {

	private final CompanySettingsRepository repository;

	
	 public String saveFile(MultipartFile file, String folder) throws Exception {
	        String upload = System.getProperty("user.dir") + "/" + folder + "/";
	        File dir = new File(upload);
	        if (!dir.exists()) dir.mkdirs();
	        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
	        file.transferTo(new File(upload + fileName));
	        return folder + "/" + fileName;
	    }
	
	public ResponseEntity<?> createCompanyDetails(CompanySettings request, MultipartFile logo){
		
		if(logo != null && !logo.isEmpty()) {
			try {
				String fileName = saveFile(logo, "companylogo");
				request.setCompanyLogo(fileName);

			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Image upload failed "+e);
			}
		}
		
		CompanySettings res = repository.save(request);
		return ResponseEntity.ok(res);
	}

	public ResponseEntity<?> updateDetails(Long id, CompanySettings request, MultipartFile logo){
		
		Optional<CompanySettings> exist = repository.findById(id);
		if(exist.isEmpty()) {
			return ResponseEntity.badRequest().body("Company details not found with id: "+id);
		}
		
		CompanySettings existing = exist.get();
		if(logo != null && !logo.isEmpty()) {
			try {
				String fileName = saveFile(logo, "companylogo");
				existing.setCompanyLogo(fileName);

			}
			catch(Exception e) {
				return ResponseEntity.status(500).body("Image upload failed "+e);
			}
		}
		
		existing.setCompanyName(request.getCompanyName());
		existing.setRegisterNumber(request.getRegisterNumber());
		existing.setDepartment(request.getDepartment());
		existing.setGstNumber(request.getGstNumber());
		existing.setCompanyEmail(request.getCompanyEmail());
		existing.setCompanyNumber(request.getCompanyNumber());
		existing.setWebsiteUrl(request.getWebsiteUrl());
		existing.setCity(request.getCity());
		existing.setState(request.getState());
		existing.setPincode(request.getPincode());
		existing.setAddress(request.getAddress());
		existing.setCreatedAt(request.getCreatedAt());
		existing.setUpdatedAt(request.getUpdatedAt());
		
		CompanySettings res = repository.save(existing);
		return ResponseEntity.ok(res);
		
	}
	
	
	public List<CompanySettings> getAll() {
        return repository.findAll();
    }
	
	public List<CompanyDetailDTO> getAllDetail() {

	    List<CompanySettings> settings = repository.findAll();

	    return settings.stream().map(company -> {
	        CompanyDetailDTO dto = new CompanyDetailDTO();
	        dto.setCompanyName(company.getCompanyName());
	        dto.setCompanyLogo(company.getCompanyLogo());
	        return dto;
	    }).toList();
	}

    public CompanySettings getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    public String deleteById(Long id) {
        CompanySettings company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        repository.delete(company);
        return "Company deleted successfully";
    }
	
	
	
	
}
