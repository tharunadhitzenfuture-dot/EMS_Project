package com.example.EMS.Service.CompanySettings;

import com.example.EMS.EmployeeDTO.CompanyDetailDTO;
import com.example.EMS.Entity.CompanySettings;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CompanySettingsService {

    String saveFile(MultipartFile file, String folder) throws Exception;

    ResponseEntity<?> createCompanyDetails(CompanySettings request, MultipartFile logo);

    ResponseEntity<?> updateDetails(Long id, CompanySettings request, MultipartFile logo);

    List<CompanySettings> getAll();

    List<CompanyDetailDTO> getAllDetail();

    CompanySettings getById(Long id);

    String deleteById(Long id);

}