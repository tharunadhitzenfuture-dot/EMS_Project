package com.example.EMS.EmployeeService;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.EmployeeEntity.Attendance;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;
import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyReportDTO;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveRequestRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.EmployeeRepository.WeeklyCalculations.WeeklyCalculationRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveRequestService;
import com.example.EMS.enums.LeaveType;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
	
	private AttendanceRepository attendanceRepo;
	private EmpRepository empRepo;
	private WeeklyCalculationRepository weekly;
	private LeaveRequestService leaveService;
	private PermissionRepository permissionRepository;
	private LeaveRequestRepository leaveRepository;

	
	



	 public AttendanceService(AttendanceRepository attendanceRepo, EmpRepository empRepo,
			WeeklyCalculationRepository weekly, LeaveRequestService leaveService,
			PermissionRepository permissionRepository, LeaveRequestRepository leaveRepository) {
		this.attendanceRepo = attendanceRepo;
		this.empRepo = empRepo;
		this.weekly = weekly;
		this.leaveService = leaveService;
		this.permissionRepository = permissionRepository;
		this.leaveRepository = leaveRepository;
	}




	 public Long getIdByEmployeeId(String empId) {
	    	return empRepo.findIdByEmployeeId(empId);
	    }




	public ResponseEntity<?> registerService(Employee emp,LocalDate date, LocalTime checkIn,LocalTime checkOut, String status){
		
		LocalDate today;
		if(date == null) {
			today = LocalDate.now();
		}
		else {
			today = date;
		}
		      
        boolean alreadyExists =attendanceRepo.findByEmployee_EmployeeIdAndAttendanceDate(emp.getEmployeeId(),today).isPresent();

        if (alreadyExists) {

            return ResponseEntity.badRequest()
                    .body("Attendance already marked today");
        }

        Attendance attendance = new Attendance();

        attendance.setEmployee(emp);

        attendance.setEmpName(
                emp.getFirst_name() + " "
                + emp.getLast_name());

        attendance.setDepartment(
                emp.getProfessional_details()
                   .getProfessional_department());

        attendance.setDesignation(
                emp.getProfessional_details().getProfessional_designation());

        attendance.setAttendanceDate(today);

        attendance.setCheckIn(checkIn);

        attendance.setCheckOut(checkOut);

     
        //attendance.setStatus(status);

       
        if (checkIn != null && checkOut != null) {
        	
        	

            Duration duration =
                    Duration.between(checkIn, checkOut);
            
            long hours = duration.toHours();
        	long minutes = duration.toMinutesPart();
        	long seconds = duration.toSecondsPart();
        	
        	String totalTime =  String.format(
        			"%02d:%02d:%02d",
        			hours,
        			minutes,
        			seconds
        			);

            attendance.setTotalWorkingHours(
                    totalTime);
            
          
            Optional<Permission> permission = permissionRepository.findByPermissionDateAndEmployee_Id(today, emp.getId());
          
            
            if(permission == null || permission.isEmpty()) {
            	  if(emp.getProfessional_details().getProfessional_department().equals("IT")) {
               	   Optional<WeeklyCalculation> week = weekly.findByDeptName("IT");
               	   if(week.isEmpty()) {
               		   return ResponseEntity.badRequest().body("There is no weekly record for IT");
               	   }
               	   WeeklyCalculation res = week.get();
               	   
               	   String[] parts = res.getWorkHours().split(":");

                      if (parts.length != 3) {
                          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                  .body("workHours must be in HH:mm:ss format");
                      }

                      long hour_Per_Day = Long.parseLong(parts[0]);
               	   
               	   if(hours < hour_Per_Day/2) {
               		   LeaveRequest request = new LeaveRequest();
               		   
               		   request.setStartDate(date);
               		   request.setEndDate(date);
               		   request.setLeaveType(LeaveType.FULL_DAY);
               		   request.setReason("Auto-generated due to insufficient work hours "+ totalTime+" on: "+date);
               		   
               		   attendance.setStatus(LeaveType.ABSENT);
               		   leaveService.applyLeave(emp.getEmployeeId(), request);
               		   
               	   }
               	   else if(hours < hour_Per_Day) {
               		   LeaveRequest request = new LeaveRequest();
               		   
               		   request.setStartDate(date);
               		   request.setEndDate(date);
               		   request.setLeaveType(LeaveType.HALF_DAY);
               		   request.setReason("Auto-generated due to insufficient work hours "+totalTime+" on: "+date);
               		   
               		   attendance.setStatus(LeaveType.HALF_DAY);
               		   leaveService.applyLeave(emp.getEmployeeId(), request);
               		   
               		   
               		   
               	   }
               	   else {
               		   attendance.setStatus(LeaveType.PRESENT);
               	   }
               	   
               	   
                  }
                  else if(emp.getProfessional_details().getProfessional_department().equals("Insurance")) {
               	   Optional<WeeklyCalculation> week = weekly.findByDeptName("Insurance");
               	   if(week.isEmpty()) {
               		   return ResponseEntity.badRequest().body("There is no weekly record for insurance");
               	   }
               	   WeeklyCalculation res = week.get();
               	   String[] parts = res.getWorkHours().split(":");

                      if (parts.length != 3) {
                          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                  .body("workHours must be in HH:mm:ss format");
                      }

                      long hour = Long.parseLong(parts[0]);
                      

               	   long n = hour/res.getTotalWorkDays();
               	   
               	  
               	   if(hours < n/2) {
               		   LeaveRequest request = new LeaveRequest();
               		   
               		   request.setStartDate(date);
               		   request.setEndDate(date);
               		   request.setLeaveType(LeaveType.FULL_DAY);
               		   request.setReason("Auto-generated due to insufficient work hours "+totalTime +" on: "+date);
               		   
               		   attendance.setStatus(LeaveType.ABSENT);
               		   leaveService.applyLeave(emp.getEmployeeId(), request);
               		   
               	   }
               	   else  if(hours < n) {
               		   
               		   LeaveRequest request = new LeaveRequest();
               		   
               		   request.setStartDate(date);
               		   request.setEndDate(date);
               		   request.setLeaveType(LeaveType.HALF_DAY);
               		   request.setReason("Auto-generated due to insufficient work hours "+totalTime+" on: "+date);
               		   
               		   attendance.setStatus(LeaveType.HALF_DAY);
               		   
               		   leaveService.applyLeave(emp.getEmployeeId(), request);
               		           		   
               	   }
               	   else {
               		   attendance.setStatus(LeaveType.PRESENT);
               	   }
                  }
            }
           
 
            
        }

        attendanceRepo.save(attendance);
        
        

        return ResponseEntity.ok(
                "Attendance Registered Successfully");
		
		
	}
	
	public ResponseEntity<?> getAllAttendance() {
        List<Attendance> attendanceList = attendanceRepo.findAll();

        if(attendanceList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No attendance records found");
        }

        return ResponseEntity.ok(attendanceList);
    }


   
    public ResponseEntity<?> getAttendanceById(String empId) {

    	Optional<List<Attendance>> attendance = attendanceRepo.findAllByEmployee_EmployeeId(empId);

        if(attendance.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Attendance not found with id : " + empId);
        }

        return ResponseEntity.ok(attendance.get());
    }


   
    @Transactional
    public ResponseEntity<?> deleteAttendanceById(String empId) {

    	Optional<List<Attendance>> attendance = attendanceRepo.findAllByEmployee_EmployeeId(empId);

        if(attendance.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Attendance not found with id : " + empId);
        }

        attendanceRepo. deleteByEmployee_EmployeeId(empId);

        return ResponseEntity.ok(
                "Attendance deleted successfully");
    }

    
    public ResponseEntity<?> updateAttendance(
    		Employee emp,
            String empId,
            AttendanceRequestDTO request){

    	LocalDate today;
		if(request.getDate() == null) {
			today = LocalDate.now();
		}
		else {
			today = request.getDate();
		}
  

        Optional<Attendance> attendanceOpt =
                attendanceRepo
                .findByEmployee_EmployeeIdAndAttendanceDate(
                        empId,
                        today);

        if(attendanceOpt.isEmpty()){

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Attendance not found");
        }

        Attendance attendance = attendanceOpt.get();

        
      
        if(request.getCheckIn() != null){

            attendance.setCheckIn(
                    request.getCheckIn());
        }

        
        
        if(request.getCheckOut() != null){

            attendance.setCheckOut(
                    request.getCheckOut());
        }

        
        
        if(attendance.getCheckIn() != null
                && attendance.getCheckOut() != null){

            Duration duration =
                    Duration.between(
                            attendance.getCheckIn(),
                            attendance.getCheckOut());
            
            long hours = duration.toHours();
        	long minutes = duration.toMinutesPart();
        	long seconds = duration.toSecondsPart();
        	
        	String totalTime =  String.format(
        			"%02d:%02d:%02d",
        			hours,
        			minutes,
        			seconds
        			);

            attendance.setTotalWorkingHours(
                    totalTime);
            
            if(emp.getProfessional_details().getProfessional_department().equals("IT")) {
         	   Optional<WeeklyCalculation> week = weekly.findByDeptName("IT");
         	   if(week.isEmpty()) {
         		   return ResponseEntity.badRequest().body("There is no weekly record for IT");
         	   }
         	   WeeklyCalculation res = week.get();
         	  String[] parts = res.getWorkHours().split(":");

              if (parts.length != 3) {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body("workHours must be in HH:mm:ss format");
              }

              long hour_Per_Day = Long.parseLong(parts[0]);
              

              if(hours < hour_Per_Day/2) {
         		  LeaveRequest requestDTO = new LeaveRequest();
       		   
       		   requestDTO.setStartDate(attendance.getAttendanceDate());
       		   requestDTO.setEndDate(attendance.getAttendanceDate());
       		   requestDTO.setLeaveType(LeaveType.FULL_DAY);
       		   requestDTO.setReason("Auto-generated due to insufficient work hours "+hours+" on: "+attendance.getAttendanceDate());
       		   
       		   attendance.setStatus(LeaveType.ABSENT);
       		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
       		   
       		   
       		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
       		   
       	       }
              else if(hours < hour_Per_Day) {
         		   
         		   LeaveRequest requestDTO = new LeaveRequest();
         		   requestDTO.setStartDate(attendance.getAttendanceDate());
         		   requestDTO.setEndDate(attendance.getAttendanceDate());
         		   requestDTO.setLeaveType(LeaveType.HALF_DAY);
         		   requestDTO.setReason("Auto-generated due to insufficient work hours on: "+attendance.getAttendanceDate());
         		   
         		   attendance.setStatus(LeaveType.HALF_DAY);
         		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
         		   
         		   
         		   
         	   }
         	   
         	   else {
         		   attendance.setStatus(LeaveType.PRESENT);
         	   }
         	   
         	   
            }
            else if(emp.getProfessional_details().getProfessional_department().equals("Insurance")) {
         	   Optional<WeeklyCalculation> week = weekly.findByDeptName("Insurance");
         	   if(week.isEmpty()) {
         		   return ResponseEntity.badRequest().body("There is no weekly record for insurance");
         	   }
         	   WeeklyCalculation res = week.get();
         	  String[] parts = res.getWorkHours().split(":");

              if (parts.length != 3) {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body("workHours must be in HH:mm:ss format");
              }

              long hour_Per_Day = Long.parseLong(parts[0]);
  
              
              if(hours < hour_Per_Day/2) {
         		  LeaveRequest requestDTO = new LeaveRequest();
       		   
       		   requestDTO.setStartDate(attendance.getAttendanceDate());
       		   requestDTO.setEndDate(attendance.getAttendanceDate());
       		   requestDTO.setLeaveType(LeaveType.FULL_DAY);
       		   requestDTO.setReason("Auto-generated due to insufficient work hours "+hours+" on: "+attendance.getAttendanceDate());
       		   
       		   attendance.setStatus(LeaveType.ABSENT);
       		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
       		   
       		   
       		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
       		   
       	   }
         	   
              else if(hours < hour_Per_Day) {
         		   
         		   LeaveRequest requestDTO = new LeaveRequest();
         		   
         		   requestDTO.setStartDate(attendance.getAttendanceDate());
         		   requestDTO.setEndDate(attendance.getAttendanceDate());
         		   requestDTO.setLeaveType(LeaveType.HALF_DAY);
         		   requestDTO.setReason("Auto-generated due to insufficient work hours "+hours+" on: "+attendance.getAttendanceDate());
         		   
         		   attendance.setStatus(LeaveType.HALF_DAY);
         		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
         		   
         		   
         		   leaveService.applyLeave(emp.getEmployeeId(), requestDTO);
         		           		   
         	   }
         	 
         	   
         	   else {
         		   attendance.setStatus(LeaveType.PRESENT);
         	   }
            }
        }

        
        
//        if(request.getStatus() != null){
//
//            attendance.setStatus(
//                    request.getStatus());
//        }

        attendanceRepo.save(attendance);

        return ResponseEntity.ok(
                "Attendance updated successfully");
    }
    
    
    
    public ResponseEntity<?> getWeeklyHours(WorkingHoursDTO request){
    	Long empId = empRepo.findIdByEmployeeId(request.getEmpId());
//    	Double hours = attendanceRepo.calculateWeeklyHours(empId, request.getStartDate(), request.getEndDate());
    	
        List<Attendance> records =
    	        attendanceRepo.findByEmployeeIdAndAttendanceDateBetween(
    	                empId,
    	                request.getStartDate(),
    	                request.getEndDate()
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
    	
    	return ResponseEntity.ok("Employee ID: "+request.getEmpId()+" worked from "+ request.getStartDate()+" to "+request.getEndDate()+" totally "+weeklyHours+" hours");
    }
	
    
    public ResponseEntity<?> getEmpPresent(LocalDate date){
    	Long count = attendanceRepo.countPresentEmployees(date);
    	return ResponseEntity.ok(count);
    }
    

    public ResponseEntity<?> calculateWeeklyHours(String empId) {
    	
    	 LocalDate today = LocalDate.now();
    	 Long empid = empRepo.findIdByEmployeeId(empId);

         LocalDate startOfWeek =
                 today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

         LocalDate endOfWeek =
                 today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
         
//         Double hours = attendanceRepo.calculateWeeklyHours(empid, startOfWeek, endOfWeek);
         
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
     	
     	return ResponseEntity.ok("Employee ID: "+empId+" worked from "+ startOfWeek+" to "+endOfWeek+" totally :"+weeklyHours);
    		
    }
    
    
 
	

	
}
