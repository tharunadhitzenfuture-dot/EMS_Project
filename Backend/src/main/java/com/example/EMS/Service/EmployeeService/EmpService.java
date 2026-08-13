package com.example.EMS.Service.EmployeeService;

import com.example.EMS.Entity.Employee;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EmpService {

    Long getIdByEmployeeId(String empId);

    ResponseEntity<?> createUser(Employee emp);

    int getJobLevel(String email);

    ResponseEntity<?> createEmpIMG(Employee emp, MultipartFile file, MultipartFile aadhar, MultipartFile pan_card, List<MultipartFile> higherEducation, List<MultipartFile> bankStatement, List<MultipartFile> salarySlip, MultipartFile passbook, MultipartFile education, MultipartFile resume, MultipartFile offerLetter, List<MultipartFile> prevExpLetter, List<MultipartFile> experienceLetter);

    ResponseEntity<?> createUserXL(MultipartFile xlFile, List<MultipartFile> file, List<MultipartFile> aadhar, List<MultipartFile> pan_card, List<MultipartFile> higherEducation, List<MultipartFile> bankStatement, List<MultipartFile> salarySlip, List<MultipartFile> passbook, List<MultipartFile> education, List<MultipartFile> resume, List<MultipartFile> offerLetter, List<MultipartFile> prevExpLetter, List<MultipartFile> higherCertification, List<MultipartFile> experienceLetter);

    ResponseEntity<?> getAllEmployeeDetails();

    ResponseEntity<?> getEmployeeById(String id);

    ResponseEntity<?> getPayrollById(String empId);

    ResponseEntity<?> deleteEmployeeById(String id);

    ResponseEntity<?> updateEmployee(String empId, Employee emp);

    ResponseEntity<?> updateEmployeeImage(String empId, MultipartFile image) throws Exception;

    ResponseEntity<?> updateEmployeeFile(String empId, MultipartFile file, String fileType) throws Exception;

    ResponseEntity<?> updateEmployeeAll(String empId, Employee emp, MultipartFile image, MultipartFile aadhar, MultipartFile panCard, List<MultipartFile> higherEducation, List<MultipartFile> prevExpLetter, List<MultipartFile> bankStatement, List<MultipartFile> salarySlip, MultipartFile passbookPdf, MultipartFile educationPdf, MultipartFile resume, MultipartFile offerLetter, List<MultipartFile> expLetter) throws Exception;

    String saveFile(MultipartFile file, String folder) throws Exception;

    double calculateAnnualCTC(double basicPay, double HRA, double specialAllowance, double LTA, double PF, double medicalAllowance, double bonus);

    ResponseEntity<?> updateUserXL(MultipartFile xlFile, List<MultipartFile> file, List<MultipartFile> aadhar, List<MultipartFile> pan_card, List<MultipartFile> passbook, List<MultipartFile> education, List<MultipartFile> higherEducation, List<MultipartFile> resume, List<MultipartFile> offerLetter, List<MultipartFile> prevExpLetter, List<MultipartFile> experienceLetter, List<MultipartFile> bankStatement, List<MultipartFile> higherCertification, List<MultipartFile> salarySlip);

}