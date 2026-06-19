package com.example.EMS.EmployeeController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMS.EmployeeRepository.ShiftEmployeeDetailsRepository;
import com.example.EMS.EmployeeService.ShiftEmployeeDetailsService;

@RestController
@RequestMapping("/api/employeeShift")
public class ShiftEmployeeDetailsController {
	
	private final ShiftEmployeeDetailsService shiftService;
	private final ShiftEmployeeDetailsRepository shiftRepo;
	
	public ShiftEmployeeDetailsController(ShiftEmployeeDetailsService shiftService,
			ShiftEmployeeDetailsRepository shiftRepo) {
		this.shiftService = shiftService;
		this.shiftRepo = shiftRepo;
	}
	
	
	
	@PostMapping(value = "/uploadExcel", consumes = "multipart/form-data")
	public ResponseEntity<?> register(@RequestPart(value = "xlFile") MultipartFile xlFile) throws Exception{
		return ResponseEntity.ok(shiftService.bulkUpload(xlFile));
		
	}

}
