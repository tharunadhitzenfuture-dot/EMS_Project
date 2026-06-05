package com.example.EMS.EmployeeService.WeeklyCalculations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Attendance;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyReportDTO;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.EmployeeRepository.WeeklyCalculations.WeeklyCalculationRepository;
import com.example.EMS.enums.LeaveStatus;

@Service
public class WeeklyReportService {
	
	private final EmpRepository empRepo;
	private final AttendanceRepository attendanceRepo;
	private final WeeklyCalculationRepository weeklyCalculation;
	private final PermissionRepository permissionRepository;

	 public WeeklyReportService(EmpRepository empRepo, AttendanceRepository attendanceRepo,
			WeeklyCalculationRepository weeklyCalculation, PermissionRepository permissionRepository) {
		
		this.empRepo = empRepo;
		this.attendanceRepo = attendanceRepo;
		this.weeklyCalculation = weeklyCalculation;
		this.permissionRepository = permissionRepository;
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
	             
	             req.setStartDate(start);
	             req.setEndDate(end);
	             
	            
	    		
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
	    		

	            String permission =  getPermissionHours(empId, startOfWeek, endOfWeek);	            
	            //Permission
	            req.setPermission(permission);
	            
	            
	            String dept = emp.get().getProfessional_details().getProfessional_department();
	            
	            if(!dept.equalsIgnoreCase("IT") && !dept.equalsIgnoreCase("Finance")) {
	            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee id: "+emp.get().getEmployeeId()+" department mismatched with: "+dept);
	            }
	       
	            String total = getByDepartment(emp.get().getProfessional_details().getProfessional_department());
	            
	            req.setDepartment_workHours(total);
	                                
	                              //9:00:00                 //8:30:00
	           long balance =   timeToSeconds(weeklyHours) -  timeToSeconds(total);
	           
	          
	        	   long sec = timeToSeconds(weeklyHours) - timeToSeconds(total);  
	        	                     
	        	                     //00:15:00
	        	   if(timeToSeconds(permission) >0) {
	        		   long compensation = sec - timeToSeconds(permission);
	        		   sec = compensation;
	        		   
	        		   //Compensation
	        		   if(compensation >= timeToSeconds(permission)) {
	        			   req.setCompensation(permission);
	        			   
	        		   }
	        		   else {
//	        			   System.out.println("Emp: "+empId+" "+permission+" "+secondsToTime(compensation));
//	        			   long comp = timeToSeconds(permission) - compensation;
//	        			   req.setCompensation(secondsToTime(comp));
	        			   req.setCompensation("Total working hours didn't meet compensation hours");
	        		   }
	        		   
	        		        
	        	   }
	        	   else {
	        		   req.setCompensation(secondsToTime(0));
	        	   }
	        	   
	        	   if(sec >= 0) {
	        		   //OverTime
	        		   req.setOverTime(secondsToTime(sec));
	        		   req.setShortFall("00:00:00");
	        	   }
	        	   else {
	        		   //Shortfall
	        			 req.setShortFall(secondsToTime(sec)); 
	        			 req.setOverTime("00:00:00");
	        	   }
	        	   
	        	   
	        	   if(req.getShortFall().equals("00:00:00")) {
	        		   //status
	        		   req.setStatus("Completed");
	        	   }
	        	   else {
	        		 //status
	        		   req.setStatus("Pending");   
	        	   }
  
	           

	    		dto.add(req);
	    		
	    		
	    	}
	    	
	    	return ResponseEntity.ok(dto);

	    	
	    }
	 
	 
	 public static long timeToSeconds(String time) {

		    if (time == null || time.isBlank()) {
		        return 0;
		    }

		    String[] parts = time.split(":");

		    long hours = Long.parseLong(parts[0]);
		    long minutes = Long.parseLong(parts[1]);
		    long seconds = Long.parseLong(parts[2]);

		    return (hours * 3600) + (minutes * 60) + seconds;
		}
	 
	 public static String secondsToTime(long totalSeconds) {

		    long hours = totalSeconds / 3600;
		    long minutes = (totalSeconds % 3600) / 60;
		    long seconds = totalSeconds % 60;

		    return String.format(
		            "%02d:%02d:%02d",
		            hours,
		            minutes,
		            seconds
		    );
		}
	 
	 
	 public String getPermissionHours(String empId, LocalDate start, LocalDate end) {
		long sec = 0;
		Long id = empRepo.findIdByEmployeeId(empId);
		
		List<Permission> hoursList =  permissionRepository.findByEmployeeIdAndStartDateAndEndDate(id, start, end);
		for(Permission hours: hoursList) {
			if( hours.getStatus() == LeaveStatus.APPROVED) {
				sec += timeToSeconds(hours.getHours());
			}
		}
		
		
		
		return secondsToTime(sec);
	 }
	 
	

	  public String getByDepartment(String dept) {
		  Optional<WeeklyCalculation> calcStart =  weeklyCalculation.findByDeptName(dept);		  
		  WeeklyCalculation res = calcStart.get();
		  return res.getTotalWorkHours();
	  }
	  
	  public Long getHours(String hour) {
		  String[] parts = hour.split(":");

          long hr = Long.parseLong(parts[0]);

   	      return hr;
	  }

}
