

package com.example.EMS.EmployeeController.WeeklyCalculations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;
import com.example.EMS.EmployeeService.WeeklyCalculationService;

@RestController
@RequestMapping("/api/weekly")
public class WeeklyCalculationController {
	
	private final WeeklyCalculationService calcService;
	
	

	public WeeklyCalculationController(WeeklyCalculationService calcService) {
		this.calcService = calcService;
	}



	@PostMapping("/create")
	public ResponseEntity<?> createWeeklyRecord(@RequestBody WeeklyCalculation request){
	
		if (request.getStartDate() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter start date");
		}

		if (request.getEndDate() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter end date");
		}

		if (request.getTotalWorkDays() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter total work days");
		}

		if (request.getTotalWorkHours() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter total work hours");
		}
		
		return calcService.createWeeklyRecord(request);
			
		
	}
	
	@GetMapping("/getHours")
	public ResponseEntity<?> getWeeklyTotalHours(@RequestBody WorkingHoursDTO request){
		
		if (request.getStartDate() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter start date");
		}

		if (request.getEndDate() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter end date");
		}
       
		return calcService.getWeeklyHours(request);
	}
}
