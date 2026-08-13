package com.example.EMS.Service.EmployeeInvite;

import com.example.EMS.Entity.EmployeeInvite;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeInviteService {

    String saveFile(MultipartFile file, String folder) throws Exception;

    ResponseEntity<?> saveEmployee(EmployeeInvite empInvite, MultipartFile file, MultipartFile aadhar, MultipartFile pan_card, List<MultipartFile> higherEducation, List<MultipartFile> bankStatement, List<MultipartFile> salarySlip, MultipartFile passbook, MultipartFile education, MultipartFile resume, MultipartFile offerLetter, List<MultipartFile> prevExpLetter, List<MultipartFile> experienceLetter);

    ResponseEntity<?> getAllForm();

    ResponseEntity<?> getFormById(Long id);

    ResponseEntity<?> deleteFormById(Long id);

    ResponseEntity<?> updateFormById(Long id, EmployeeInvite empInvite, MultipartFile file, MultipartFile aadhar, MultipartFile pan_card, List<MultipartFile> higherEducation, List<MultipartFile> bankStatement, List<MultipartFile> salarySlip, MultipartFile passbook, MultipartFile education, MultipartFile resume, MultipartFile offerLetter, List<MultipartFile> prevExpLetter, List<MultipartFile> experienceLetter) throws Exception;

    ResponseEntity<?> convert(List<Long> lst);

    ResponseEntity<?> convertByOne(Long id);

}