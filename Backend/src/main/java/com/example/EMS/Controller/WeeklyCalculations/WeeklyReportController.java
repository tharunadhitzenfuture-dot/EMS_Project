package com.example.EMS.Controller.WeeklyCalculations;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.Entity.Employee;
import com.example.EMS.Repository.EmpRepository;
import com.example.EMS.Service.WeeklyCalculations.WeeklyReportService;

@RestController
@RequestMapping("/api/weeklyReport")
@RequiredArgsConstructor
public class WeeklyReportController {
	
	private final WeeklyReportService reportService;
	private final EmpRepository empRepo;


	@GetMapping("/getWeeklyReport")
	public ResponseEntity<?> getWeeklyReport(){

		 List<Employee> lst =  empRepo.findAll();
		 List<String> ids = new ArrayList<>();
		 
		 for(Employee emp: lst) {
			String id =  emp.getEmployeeId();
			ids.add(id);
		 }

		 return reportService.getWeeklyReport(ids);

	  }


}
