package com.example.EMS.EmployeeService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeDTO.BulkUploadResponseDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.EmployeeShift;
import com.example.EMS.EmployeeEntity.ShiftEmployeeDetails;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.ShiftEmployeeDetailsRepository;
import com.example.EMS.EmployeeRepository.ShiftTimingRepository;

@Service
public class ShiftEmployeeDetailsService {
	
	    private final EmpRepository empRepository;
	    private final ShiftEmployeeDetailsRepository shiftRepository;
	    private final ShiftTimingRepository shiftTimingRepository;
	    
	    

	  public ShiftEmployeeDetailsService(EmpRepository empRepository, ShiftEmployeeDetailsRepository shiftRepository,
				ShiftTimingRepository shiftTimingRepository) {
			this.empRepository = empRepository;
			this.shiftRepository = shiftRepository;
			this.shiftTimingRepository = shiftTimingRepository;
		}



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

	            ShiftEmployeeDetails details = new ShiftEmployeeDetails();

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

	
}
