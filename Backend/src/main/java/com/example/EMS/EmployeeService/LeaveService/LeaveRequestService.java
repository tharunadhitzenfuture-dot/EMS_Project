package com.example.EMS.EmployeeService.LeaveService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.EmployeeEntity.Attendance;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeException.BadRequestException;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveBalanceRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveRequestRepository;
import com.example.EMS.EmployeeService.AttendanceService;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveType;
import com.example.EMS.enums.Role;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestService {
	
	private final EmpRepository empRepository;
	private final LeaveBalanceRepository leaveBalanceRepository;
	private final LeaveRequestRepository leaveRequestRepository;
	private final LeaveRecordService leaveRecord;
	private final AttendanceRepository attRepository;
	

	


	

	public LeaveRequestService(EmpRepository empRepository, LeaveBalanceRepository leaveBalanceRepository,
			LeaveRequestRepository leaveRequestRepository, LeaveRecordService leaveRecord,
			AttendanceRepository attRepository) {
		this.empRepository = empRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.leaveRecord = leaveRecord;
		this.attRepository = attRepository;
	}


	public ResponseEntity<?> applyLeave(String empId, LeaveRequest request){
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		
		List<LeaveRequest> res = leaveRequestRepository.findOverlappingLeaves(id, request.getStartDate(), request.getEndDate());
		if(!res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave request dates overlapping with dates: "+request.getStartDate()+" and "+request.getEndDate());
		}
		
//		 if (request.getStartDate().isBefore(LocalDate.now()))
//	            throw new BadRequestException("Start date cannot be in the past");
		 
        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new BadRequestException("End date must be on or after start date");
        
        int workingDays = countWorkingDays(request.getStartDate(), request.getEndDate());
        int month = request.getStartDate().getMonthValue();
        int year = request.getStartDate().getYear();
        

        LeaveBalance balance = leaveBalanceRepository
                .findByEmployeeIdAndMonthAndYear(emp.getId(),month, year);
        
//        if (balance.getRemainingDays() < workingDays)
//            throw new BadRequestException("Insufficient balance. Available: "
//                    + balance.getRemainingDays() + ", Requested: " + workingDays);
//        
        
        request.setEmployee(emp);
        LeaveRequest req = leaveRequestRepository.save(request);
        return ResponseEntity.ok(req);
        
	      	
		
	}
	
	 
	    @Transactional
	    public ResponseEntity<?> reviewLeave(String empId, Long leaveId, ReviewLeaveDto dto) {   
	    	Long id = empRepository.findIdByEmployeeId(empId);
	        Employee emp = getUserByEmployeeId(id);
	        LeaveRequest req = getLeaveById(leaveId);
	        
	        
	        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
	        }
	        
	        

	        if (req.getStatus() == LeaveStatus.CANCELLED)
	            throw new BadRequestException("Cannot review a cancelled request");
	        if (req.getStatus() == LeaveStatus.APPROVED || req.getStatus() == LeaveStatus.REJECTED)
	            throw new BadRequestException("Request has already been reviewed");
	        if (dto.getStatus() != LeaveStatus.APPROVED && dto.getStatus() != LeaveStatus.REJECTED)
	            throw new BadRequestException("Status must be APPROVED or REJECTED");
	        
	        if (dto.getStatus() == LeaveStatus.APPROVED) {

	            LocalDate current = req.getStartDate();

	            while ( current.isBefore(req.getEndDate()) ||
	            	    current.isEqual(req.getEndDate())) {

	                int month = current.getMonthValue();
	                int year = current.getYear();

	                LeaveBalance balance =
	                        leaveBalanceRepository
	                        .findByEmployeeIdAndMonthAndYear(
	                                req.getEmployee().getId(),
	                                month,
	                                year
	                        );

	                if (balance == null) {

	                    throw new BadRequestException(
	                            "Leave balance not found for "
	                            + month + "/" + year
	                    );
	                }




	                

	           
	                balance.setUsedDays(
	                        balance.getUsedDays() + 1);
	          


	                balance.setRemainingDays(balance.getRemainingDays()); 
	                leaveBalanceRepository.save(balance);

	               
	                current = current.plusDays(1);
	                
	                
	               
	            }
	            if(req.getReason() == null || !req.getReason().startsWith("Auto-generated")) {
	            	 for (LocalDate date = req.getStartDate();!date.isAfter(req.getEndDate());date = date.plusDays(1)) {
	 	            	Optional<Attendance> record =attRepository.findByEmployee_EmployeeIdAndAttendanceDate(req.getEmployee().getEmployeeId(), date);
	 	            	if(record.isEmpty()) {
	 	            		leaveRecord.markAbsent(
	 			        		    req.getEmployee(),
	 			        		    date,
	 			        		    LocalTime.parse("00:00:00"),
	 			        		    LocalTime.parse("00:00:00"),
	 			        		    LeaveType.ABSENT.name()
	 			        		);
	 	            	}
	 	            	else {
	 	            		AttendanceRequestDTO dto1 = new AttendanceRequestDTO();
	 	            		dto1.setEmpId(req.getEmployee().getEmployeeId());
	 	            		dto1.setCheckIn(LocalTime.parse("00:00:00"));
	 	            		dto1.setCheckOut(LocalTime.parse("00:00:00"));
	 	            		dto1.setDate(date);
	 	            		dto1.setStatus(LeaveType.ABSENT.name());
	 	            	    
	 	            		leaveRecord.updateAbsent(req.getEmployee(), req.getEmployee().getEmployeeId(), dto1);
	 	            	}
	 		        	
	 		        	}
	            }
	           
		        
	        }
	               

//	            if (balance.getRemainingDays() < req.getTotalDays()) {
//	            	
//	            	 throw new BadRequestException("Insufficient balance to approve");
//	            }
	               

//	           
//	        List<Attendance> attendanceList =  attendanceRepo.findByEmployeeIdAndAttendanceDateBetween(id,req.getStartDate(), req.getEndDate());
//	        
//	       for(Attendance obj: attendanceList) {
//	    	   obj.setStatus(LeaveType.ABSENT);
//	       }
	        
	     
	        
	       
	         
	        req.setStatus(dto.getStatus());
	        req.setHrRemarks(dto.getHrRemarks());
	        req.setReviewedBy(emp);
	        req.setReviewedAt(LocalDateTime.now());

	        LeaveRequest res = leaveRequestRepository.save(req);
	        return ResponseEntity.ok(res);

	    }
	    
	    
	    @Transactional
	    public ResponseEntity<?> reviewHalfDayLeave(String empId, Long leaveId, ReviewLeaveDto dto) {   
	    	Long id = empRepository.findIdByEmployeeId(empId);
	        Employee emp = getUserByEmployeeId(id);
	        LeaveRequest req = getLeaveById(leaveId);
	        
	        
	        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
	        }
	        
	        

	        if (req.getStatus() == LeaveStatus.CANCELLED)
	            throw new BadRequestException("Cannot review a cancelled request");
	        if (req.getStatus() == LeaveStatus.APPROVED || req.getStatus() == LeaveStatus.REJECTED)
	            throw new BadRequestException("Request has already been reviewed");
	        if (dto.getStatus() != LeaveStatus.APPROVED && dto.getStatus() != LeaveStatus.REJECTED)
	            throw new BadRequestException("Status must be APPROVED or REJECTED");
	        
	        if (dto.getStatus() == LeaveStatus.APPROVED) {

	            LocalDate current = req.getStartDate();

	            while ( current.isBefore(req.getEndDate()) ||
	            	    current.isEqual(req.getEndDate())) {

	                int month = current.getMonthValue();
	                int year = current.getYear();

	                LeaveBalance balance =
	                        leaveBalanceRepository
	                        .findByEmployeeIdAndMonthAndYear(
	                                req.getEmployee().getId(),
	                                month,
	                                year
	                        );

	                if (balance == null) {

	                    throw new BadRequestException(
	                            "Leave balance not found for "
	                            + month + "/" + year
	                    );
	                }

	                

	           
	                balance.setUsedDays(
	                        balance.getUsedDays() + 0.5);
	          


	                balance.setRemainingDays(balance.getRemainingDays()); 
	                leaveBalanceRepository.save(balance);

	               
	                current = current.plusDays(1);
	               
	            }
	        }
	               

//	            if (balance.getRemainingDays() < req.getTotalDays()) {
//	            	
//	            	 throw new BadRequestException("Insufficient balance to approve");
//	            }
	               

	           
	           
	        
	         
	        req.setStatus(dto.getStatus());
	        req.setHrRemarks(dto.getHrRemarks());
	        req.setReviewedBy(emp);
	        req.setReviewedAt(LocalDateTime.now());

	        LeaveRequest res = leaveRequestRepository.save(req);
	        return ResponseEntity.ok(res);

	    }
	    
	       
	 private LeaveRequest getLeaveById(Long id) {
	        return leaveRequestRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
	    }
	
	private int countWorkingDays(LocalDate start, LocalDate end) {
        int count = 0;
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1))
            if (d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY)
                count++;
        return count;
    }
	
	
	 private Employee getUserByEmployeeId(Long empId) {
	        return empRepository.findById(empId)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + empId));
	    }

}
