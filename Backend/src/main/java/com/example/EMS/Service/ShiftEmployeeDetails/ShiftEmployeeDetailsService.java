package com.example.EMS.Service.ShiftEmployeeDetails;

import com.example.EMS.EmployeeDTO.BulkUploadResponseDTO;
import com.example.EMS.Entity.ShiftEmployeeDetails;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ShiftEmployeeDetailsService {

    BulkUploadResponseDTO bulkUpload(MultipartFile file) throws IOException;

    ResponseEntity<?> addShiftEmployee(ShiftEmployeeDetails request);

    ResponseEntity<?> updateShiftEmployee(Long id, ShiftEmployeeDetails request);

}