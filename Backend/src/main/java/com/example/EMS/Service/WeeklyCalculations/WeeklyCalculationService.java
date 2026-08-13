package com.example.EMS.Service.WeeklyCalculations;

import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.Entity.WeeklyCalculations.WeeklyCalculation;
import org.springframework.http.ResponseEntity;

public interface WeeklyCalculationService {

    ResponseEntity<?> createWeeklyRecord(WeeklyCalculation request);

    ResponseEntity<?> getWeeklyHours(WorkingHoursDTO request);

}