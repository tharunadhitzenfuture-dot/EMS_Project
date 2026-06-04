package com.example.EMS.EmployeeService.WeeklyCalculations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Attendance;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyReportDTO;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;

@Service
public class WeeklyReportService {
	
	private final EmpRepository empRepo;
	private final AttendanceRepository attendanceRepo;
	
	
	
	
	public WeeklyReportService(EmpRepository empRepo, AttendanceRepository attendanceRepo) {
		this.empRepo = empRepo;
		this.attendanceRepo = attendanceRepo;
	}


	


	   public ResponseEntity<?> getWeeklyReport(List<String> ids){
	    	
	    	List<WeeklyReportDTO> dto = new ArrayList<>();
	    	LocalDate today = LocalDate.now();
	    	
	    	for(String empId:ids) {
	    		WeeklyReportDTO req = new WeeklyReportDTO();
	    		List<String> dailyHours = new ArrayList<>(
	    		        Arrays.asList(
	    		                "00:00:00",
	    		                "00:00:00",
	    		                "00:00:00",
	    		                "00:00:00",
	    		                "00:00:00",
	    		                "00:00:00",
	    		                "00:00:00"
	    		        )
	    		);
	    		
	    		//employee id
	    		req.setEmpId(empId);
	    		Optional<Employee> emp = empRepo.findByEmployeeId(empId);
	    		if(emp.isEmpty()) {
	    			return ResponseEntity.badRequest().body("Employee id with: "+empId+" not found.");
	    		}
	    		
	    		//employee Name
	    		req.setEmpName(emp.get().getFirst_name());
	    		
	    		
	    		 Long empid = empRepo.findIdByEmployeeId(empId);

	             LocalDate start =
	                     today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

	             LocalDate end =
	                     today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	             
	            
	    		
	             List<Attendance> total_list = attendanceRepo.calculateWeeklyHoursByDay(empid, start, end);
	             
	    
	             
	             for(Attendance lst: total_list) {
	            	 
	            	 int index =
	            	            lst.getAttendanceDate()
	            	                      .getDayOfWeek()
	            	                      .getValue() - 1;
	            	 
	            	 String s = lst.getTotalWorkingHours();
	            	 dailyHours.set(index, s);
	             }
	             
	             //daily hours
	             req.setHours(dailyHours);
			
	           	 
	        	

	             LocalDate startOfWeek =
	                     today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

	             LocalDate endOfWeek =
	                     today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	             
//	             Double hours = attendanceRepo.calculateWeeklyHours(empid, startOfWeek, endOfWeek);
	             
	             List<Attendance> records =
	            	        attendanceRepo.findByEmployeeIdAndAttendanceDateBetween(
	            	                empid,
	            	                startOfWeek,
	            	                endOfWeek
	            	        );
	             
	             long totalSeconds = 0;

	             for (Attendance att : records) {

	                 String[] parts =
	                         att.getTotalWorkingHours().split(":");

	                 long hours = Long.parseLong(parts[0]);
	                 long minutes = Long.parseLong(parts[1]);
	                 long seconds = Long.parseLong(parts[2]);

	                 totalSeconds +=
	                         hours * 3600 +
	                         minutes * 60 +
	                         seconds;
	             }
	             
	             long hrs = totalSeconds / 3600;
	             long mins = (totalSeconds % 3600) / 60;
	             long secs = totalSeconds % 60;

	             String weeklyHours =
	                     String.format(
	                             "%02d:%02d:%02d",
	                             hrs,
	                             mins,
	                             secs
	                     );
	             
	             req.setTotalHours(weeklyHours);
	    		
	    		//Need to implement remaining
	             
	    		
	    		dto.add(req);
	    		
	    		
	    	}
	    	
	    	return ResponseEntity.ok(dto);
	    	
	    	
	    	
	    	
	    	
	    }

}
