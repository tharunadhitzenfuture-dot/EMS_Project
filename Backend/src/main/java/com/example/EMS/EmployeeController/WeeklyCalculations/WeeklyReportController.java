package com.example.EMS.EmployeeController.WeeklyCalculations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeService.WeeklyCalculations.WeeklyReportService;

@RestController
@RequestMapping("/api/weeklyReport")
public class WeeklyReportController {
	
	private final WeeklyReportService reportService;
	private final EmpRepository empRepo;

	  public WeeklyReportController(WeeklyReportService reportService, EmpRepository empRepo) {
		
		this.reportService = reportService;
		this.empRepo = empRepo;
	}





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
