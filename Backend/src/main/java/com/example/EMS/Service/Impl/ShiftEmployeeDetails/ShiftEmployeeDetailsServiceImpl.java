package com.example.EMS.Service.Impl.ShiftEmployeeDetails;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.EMS.Service.ShiftEmployeeDetails.ShiftEmployeeDetailsService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeDTO.BulkUploadResponseDTO;
import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.EmployeeShift;
import com.example.EMS.Entity.ShiftEmployeeDetails;
import com.example.EMS.Repository.EmpRepository;
import com.example.EMS.Repository.ShiftEmployeeDetailsRepository;
import com.example.EMS.Repository.ShiftTimingRepository;


@Service
@AllArgsConstructor
public class ShiftEmployeeDetailsServiceImpl implements ShiftEmployeeDetailsService {
	
	    private final EmpRepository empRepository;
	    private final ShiftEmployeeDetailsRepository shiftRepository;
	    private final ShiftTimingRepository shiftTimingRepository;


	  public BulkUploadResponseDTO bulkUpload(MultipartFile file)
	            throws IOException {

	        BulkUploadResponseDTO response = new BulkUploadResponseDTO();

	        List<String> success = new ArrayList<>();
	        List<String> failed = new ArrayList<>();

	        Workbook workbook = WorkbookFactory.create(file.getInputStream());

	        Sheet sheet = workbook.getSheetAt(0);

	        response.setTotalRows(sheet.getLastRowNum());

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

	            Row row = sheet.getRow(i);

	            if (row == null)
	                continue;

	            String empId = row.getCell(0).getStringCellValue().trim();
	            String email = row.getCell(1).getStringCellValue().trim();
	            String name = row.getCell(2).getStringCellValue().trim();
	            String dept = row.getCell(3).getStringCellValue().trim();
	            String shift = row.getCell(4).getStringCellValue().trim();
	            
	            Optional<EmployeeShift> timing = shiftTimingRepository.findByShiftName(shift);
	            
	          
	            
	            if(timing.isEmpty()) {
	            	  failed.add(
		                        "Row " + (i + 1)
		                                + " : Shift not created ("
		                                + empId + ")");

		                continue;
	            }

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	            LocalDate start = LocalDate.parse(
	                    row.getCell(5).getStringCellValue().trim(),
	                    formatter);

	            LocalDate end = LocalDate.parse(
	                    row.getCell(6).getStringCellValue().trim(),
	                    formatter);

	            Optional<Employee> emp =
	                    empRepository.findByEmployeeIdAndEmail(empId, email);

	            if (emp.isEmpty()) {

	                failed.add(
	                        "Row " + (i + 1)
	                                + " : Employee not found ("
	                                + empId + ")");

	                continue;
	            }

	            Employee employee = emp.get();

	            ShiftEmployeeDetails details = null;
	            Optional<ShiftEmployeeDetails> existing = shiftRepository.findByEmpId(empId);
	            if(existing.isEmpty()) {
	            	details = new ShiftEmployeeDetails();
	            }
	            else {
	            	details = existing.get();
	            }
	            

	            details.setEmpId(empId);
	            details.setEmail(email);
	            details.setName(name);
	            details.setDept(dept);
	            details.setShift(shift);
	            details.setStartTime(start);
	            details.setEndTime(end);

	            details.setEmployee(employee);

	            employee.setShiftDetails(details);

	            shiftRepository.save(details);

	            success.add(empId + " uploaded");
	        }

	        workbook.close();

	        response.setSuccess(success);
	        response.setFailed(failed);
	        response.setSuccessCount(success.size());
	        response.setFailedCount(failed.size());

	        return response;
	        
	  }
	  
	  public ResponseEntity<?> addShiftEmployee(ShiftEmployeeDetails request){
		  
		  Optional<Employee> emp  =empRepository.findByEmployeeId(request.getEmpId());
		  
		  if(emp.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee details not found with id: "+request.getEmpId());
		  }
		  
		  
		  Optional<EmployeeShift> timing = shiftTimingRepository.findByShiftName(request.getShift());
		  if(timing.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Shift details not added with name: "+request.getShift());
		  }
		  Employee employee = emp.get();
		  
		  request.setEmail(employee.getEmail());
		  request.setDept(employee.getProfessional_details().getProfessional_department().getName());
		  request.setName(employee.getFirst_name()+" "+ employee.getLast_name());
		  
		  
		  
		  ShiftEmployeeDetails details = shiftRepository.save(request);
		  
		  employee.setShiftDetails(details);

		  return ResponseEntity.ok(details);
		  
	  }
	  
     public ResponseEntity<?> updateShiftEmployee(Long id, ShiftEmployeeDetails request){
    	 
    	  Optional<ShiftEmployeeDetails> exist =  shiftRepository.findById(id);
		  
		  Optional<Employee> emp  =empRepository.findByEmployeeId(exist.get().getEmpId());
		  
		  if(emp.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee details not found with id: "+request.getEmpId());
		  }
		  
		  
		  Optional<EmployeeShift> timing = shiftTimingRepository.findByShiftName(request.getShift());
		  if(timing.isEmpty()) {
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Shift details not added with name: "+request.getShift());
		  }
		  Employee employee = emp.get();

		  
		  ShiftEmployeeDetails existing = exist.get();
		  existing.setShift(request.getShift());
		  existing.setStartTime(request.getStartTime());
		  existing.setEndTime(request.getEndTime());
		  
		  ShiftEmployeeDetails details = shiftRepository.save(existing);
		  
		  employee.setShiftDetails(details);

		  return ResponseEntity.ok(details);
		  
	  }
     
	  
	  

	
}
