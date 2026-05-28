package com.example.EMS.EmployeeService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;
import com.example.EMS.EmployeeRepository.WeeklyCalculationRepository;

@Service
public class WeeklyCalculationService {
	
	private final WeeklyCalculationRepository calcRepository;
	
	public WeeklyCalculationService(WeeklyCalculationRepository calcRepository) {
		this.calcRepository = calcRepository;
	}

	public ResponseEntity<?> createWeeklyRecord(WeeklyCalculation request){
		
		Optional<WeeklyCalculation> calcStart =  calcRepository.findByStartDate(request.getStartDate());
		
		if(calcStart.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date record already presented");
		}
		
		Optional<WeeklyCalculation> calcEnd =  calcRepository.findByEndDate(request.getEndDate());
		
		if(calcEnd.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date record already presented");
		}
		
		WeeklyCalculation res = calcRepository.save(request);
		return ResponseEntity.ok(res);
		
		
		
	}
	
	public ResponseEntity<?> getWeeklyHours(WorkingHoursDTO request){
		Optional<WeeklyCalculation> calc = calcRepository.findByStartDate(request.getStartDate());
		
		if(calc.isEmpty() || calc == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no record found with start date: "+request.getStartDate());
		}
		
		WeeklyCalculation res = calc.get();
		return  ResponseEntity.ok("Total working hours with start date: "+request.getStartDate()+" is "+res.getTotalWorkHours());
		
	}

}
