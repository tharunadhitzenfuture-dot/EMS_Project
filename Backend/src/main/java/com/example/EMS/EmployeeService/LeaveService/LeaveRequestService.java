package com.example.EMS.EmployeeService.LeaveService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.LeaveRequestDTO;
import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;
import com.example.EMS.EmployeeException.BadRequestException;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.DepartmentRepository.DepartmentRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveBalanceRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveRequestRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveTypeRepository;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveTime;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestService {
	
	private final EmpRepository empRepository;
	private final LeaveBalanceRepository leaveBalanceRepository;
	private final LeaveRequestRepository leaveRequestRepository;
	private final DepartmentRepository departmentRepository;
	private final LeaveTypeRepository leaveTypeRepository;

	

	


	public LeaveRequestService(EmpRepository empRepository, LeaveBalanceRepository leaveBalanceRepository,
			LeaveRequestRepository leaveRequestRepository, DepartmentRepository departmentRepository,
			LeaveTypeRepository leaveTypeRepository) {
		this.empRepository = empRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.departmentRepository = departmentRepository;
		this.leaveTypeRepository = leaveTypeRepository;
	}

	public ResponseEntity<?> handleReviewLeave(String empId, Long leaveId, ReviewLeaveDto dto){
		
		 Long id = empRepository.findIdByEmployeeId(empId);
         Employee emp = getUserByEmployeeId(id);
         LeaveRequest req = getLeaveById(leaveId);
        
		if(req.getLeaveTime() == LeaveTime.FULL_DAY) {
			return reviewLeave(emp.getEmployeeId(), leaveId, dto);
		}
		else {
			return reviewHalfDayLeave(emp.getEmployeeId(), leaveId, dto);
		}
	}

	public ResponseEntity<?> applyLeave(String empId, LeaveRequest request){
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		
		String email1 = null;
		String email2 = null;
		if(emp.getApproval() != null) {
			email1 = emp.getApproval().getApproverEmail1();
			email2 = emp.getApproval().getApproverEmail2();
		}
		
		if(email1 == null || email1.isBlank()) {
			 throw new BadRequestException("Approver 1 not set for employee: "+empId);
		}
		else {
			request.setApproverEmail1(email1);
		}
		
		if(email2 != null && !email2.isBlank()) {
			request.setApproverEmail2(email2);
		}
		
		
		List<LeaveRequest> res = leaveRequestRepository.findOverlappingLeaves(id, request.getStartDate(), request.getEndDate());
		if(!res.isEmpty()) {
			 throw new BadRequestException("Leave request dates overlapping with dates: "+request.getStartDate()+" and "+request.getEndDate());
		}
		
//		 if (request.getStartDate().isBefore(LocalDate.now()))
//	            throw new BadRequestException("Start date cannot be in the past");
		 
        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new BadRequestException("End date must be on or after start date");
        
        boolean time = request.getLeaveTime().toString() == "FULL_DAY" ? false : true;
        double workingDays = countWorkingDays(request.getStartDate(), request.getEndDate(), time);
        int month = request.getStartDate().getMonthValue();
        int year = request.getStartDate().getYear();
        

//        LeaveBalance balance = leaveBalanceRepository
//                .findByEmployeeIdAndMonthAndYear(emp.getId(),month, year);
        if(emp.getProfessional_details() == null) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee : "+emp.getEmployeeId()+" department not set");
        }
        Departments department1 = departmentRepository
		        .findByName(emp.getProfessional_details()
		                       .getProfessional_department()
		                       .getName())
		        .orElseThrow(() -> new RuntimeException("Department not found"));
        String dept = department1.getName();
        Optional<Departments> department = departmentRepository.findByName(dept);
        if(department.isEmpty()) {
        	return ResponseEntity.badRequest().body("Employee department not available: "+department.get().getName());
        }
        else {
 
        	request.setDepartment(department.get());
        }
        
        Optional<LeaveType> leaveType = leaveTypeRepository.findByName(request.getLeaveType().getName());
        if(leaveType.isEmpty()) {
        	 throw new BadRequestException("Leave type not found with name: "+request.getLeaveType().getName());
        }
        
        Optional<LeaveBalance> balance =leaveBalanceRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(emp, year, leaveType.get(), dept );
        if(balance.isEmpty()) {
        	 throw new BadRequestException("Employee leave policy for type: "+request.getLeaveType().getName()+" department: "+dept+" not found");
        }
        
        
        
//        if (balance.getRemainingDays() < workingDays)
//            throw new BadRequestException("Insufficient balance. Available: "
//                    + balance.getRemainingDays() + ", Requested: " + workingDays);
//       
        
        
        request.setEmployee(emp);
        request.setTotalDays(workingDays);
        request.setDepartment(department.get());
        request.setLeaveType(leaveType.get());
        LeaveRequest req = leaveRequestRepository.save(request);
        
        LeaveRequestDTO dto = new LeaveRequestDTO();
        
        dto.setId(req.getId());
        dto.setEmpId(empId);
        dto.setStartDate(req.getStartDate());
        dto.setEndDate(req.getEndDate());
        dto.setTotalDays(req.getTotalDays());
        dto.setLeaveTime(req.getLeaveTime());
        dto.setLeaveType(req.getLeaveType().getName());
        dto.setDepartment(req.getDepartment());
        dto.setApproverEmail1(req.getApproverEmail1());
        dto.setApproverEmail2(req.getApproverEmail2());
        dto.setStatus(req.getStatus());
        dto.setReason(req.getReason());
        dto.setHrRemarks(req.getHrRemarks());
        return ResponseEntity.ok(dto);
        
	      	
		
	}
	
	
	public ResponseEntity<?> applyEmpLeave(LeaveRequest request){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		Optional<Employee> empUser = empRepository.findByUser(user);
		
		if(empUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User employee details not found");
		}
		
		String empId = empUser.get().getEmployeeId();
		
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		
		String email1 = null;
		String email2 = null;
		if(emp.getApproval() != null) {
			email1 = emp.getApproval().getApproverEmail1();
			email2 = emp.getApproval().getApproverEmail2();
		}
		
		
		
		if(email1 == null || email1.isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Approver 1 not set");
		}
		else {
			request.setApproverEmail1(email1);
		}
		
		if(email2 != null && !email2.isBlank()) {
			request.setApproverEmail2(email2);
		}

		
		List<LeaveRequest> res = leaveRequestRepository.findOverlappingLeaves(id, request.getStartDate(), request.getEndDate());
		if(!res.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave request dates overlapping with dates: "+request.getStartDate()+" and "+request.getEndDate());
		}
		
//		 if (request.getStartDate().isBefore(LocalDate.now()))
//	            throw new BadRequestException("Start date cannot be in the past");
		 
        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new BadRequestException("End date must be on or after start date");
        
        boolean time = request.getLeaveTime().toString() == "FULL_DAY" ? false : true;
        double workingDays = countWorkingDays(request.getStartDate(), request.getEndDate(), time);
        int month = request.getStartDate().getMonthValue();
        int year = request.getStartDate().getYear();
        

//        LeaveBalance balance = leaveBalanceRepository
//                .findByEmployeeIdAndMonthAndYear(emp.getId(),month, year);
        
        String dept = emp.getProfessional_details().getProfessional_department().getName();
        Optional<Departments> department = departmentRepository.findByName(dept);
        
        if(department.isEmpty()) {
        	return ResponseEntity.badRequest().body("Employee department should be either IT/ FINANCE/ HR");
        }
        else {
        	Departments deptEntity = department.get();

        	deptEntity.getName();   // Force Hibernate to initialize the proxy

        	request.setDepartment(deptEntity);
        }
        
        Optional<LeaveType> leaveType = leaveTypeRepository.findByName(request.getLeaveType().getName());
        
        if(leaveType.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave type not found with name: "+request.getLeaveType().getName());
        }
       
        request.setLeaveType(leaveType.get());
        
        Optional<LeaveBalance> balance = leaveBalanceRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(emp, year, request.getLeaveType(), dept);
        
        if(balance.isEmpty()) {
        	return ResponseEntity.badRequest().body("Employee leave policy for type: "+request.getLeaveType()+" not set");
        }
        
//        if (balance.getRemainingDays() < workingDays)
//            throw new BadRequestException("Insufficient balance. Available: "
//                    + balance.getRemainingDays() + ", Requested: " + workingDays);
//        
      
        request.setEmployee(emp);
        request.setTotalDays(workingDays);
        LeaveRequest req = leaveRequestRepository.save(request);
        LeaveRequestDTO dto = new LeaveRequestDTO();
        
        dto.setId(req.getId());
        dto.setEmpId(empId);
        dto.setStartDate(req.getStartDate());
        dto.setEndDate(req.getEndDate());
        dto.setTotalDays(req.getTotalDays());
        dto.setLeaveTime(req.getLeaveTime());
        dto.setLeaveType(req.getLeaveType().getName());
        dto.setDepartment(req.getDepartment());
        dto.setApproverEmail1(req.getApproverEmail1());
        dto.setApproverEmail2(req.getApproverEmail2());
        dto.setStatus(req.getStatus());
        dto.setReason(req.getReason());
        dto.setHrRemarks(req.getHrRemarks());

        return ResponseEntity.ok(dto);
        
	      	
		
	}
	
	 
	    @Transactional
	    public ResponseEntity<?> reviewLeave(String empId, Long leaveId, ReviewLeaveDto dto) {   
	    	
	    	Long id = empRepository.findIdByEmployeeId(empId);
	        Employee emp = getUserByEmployeeId(id);
	        LeaveRequest req = getLeaveById(leaveId);
	        
	        
	  
//	        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR && emp.getRole() != Role.ADMIN) {
//	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
//	        }
	        
	        if(!emp.getEmail().equalsIgnoreCase(req.getApproverEmail1()) && !emp.getEmail().equalsIgnoreCase(req.getApproverEmail2())) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("You are not authorized to approve this leave request. Only the designated approver (" 
	                    + req.getApproverEmail1() +" or "+req.getApproverEmail2()+") can perform this action.");
	        }
	        
	        
	        Optional<LeaveType> leaveType = leaveTypeRepository.findByName(req.getLeaveType().getName());
	        if(leaveType.isEmpty()) {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave type not found with name: "+req.getLeaveType().getName());
	        }
	        req.setLeaveType(leaveType.get());
	        
	        if (req.getStatus() == LeaveStatus.CANCELLED)
	            throw new BadRequestException("Cannot review a cancelled request");
	        if (req.getStatus() == LeaveStatus.APPROVED || req.getStatus() == LeaveStatus.REJECTED)
	            throw new BadRequestException("Request has already been reviewed");
	        if (dto.getStatus() != LeaveStatus.APPROVED && dto.getStatus() != LeaveStatus.REJECTED)
	            throw new BadRequestException("Status must be APPROVED or REJECTED");
	        LeaveBalance remaining = null;
	        if (dto.getStatus() == LeaveStatus.APPROVED) {

	            LocalDate current = req.getStartDate();

	            while ( current.isBefore(req.getEndDate()) ||
	            	    current.isEqual(req.getEndDate())) {

	                int month = current.getMonthValue();
	                int year = current.getYear();

//	                LeaveBalance balance =
//	                        leaveBalanceRepository
//	                        .findByEmployeeIdAndMonthAndYear(
//	                                req.getEmployee().getId(),
//	                                month,
//	                                year
//	                        );
	               
	                Optional<LeaveBalance> bal = leaveBalanceRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(req.getEmployee(), year, leaveType.get(), req.getDepartment().getName());
               
	                if (bal.isEmpty()) {

	                    throw new BadRequestException(
	                            "Leave balance not found for employee: "+req.getEmployee_Id()+"/"
	                            + req.getLeaveType() + "/" + year + "/" +  req.getDepartment()
	                    );
	                }


	                LeaveBalance balance = bal.get();

	                

	           
	                balance.setUsedDays(
	                        balance.getUsedDays() + 1);
	          


	                balance.setRemainingDays(balance.getRemainingDays()); 
	                remaining = leaveBalanceRepository.save(balance);

	               
	                current = current.plusDays(1);
	                
	                
	               
	            }
//	            if(req.getReason() == null || !req.getReason().startsWith("Auto-generated")) {
//	            	 for (LocalDate date = req.getStartDate();!date.isAfter(req.getEndDate());date = date.plusDays(1)) {
//	 	            	Optional<Attendance> record =attRepository.findByEmployee_EmployeeIdAndAttendanceDate(req.getEmployee().getEmployeeId(), date);
//	 	            	if(record.isEmpty()) {
//	 	            		leaveRecord.markAbsent(
//	 			        		    req.getEmployee(),
//	 			        		    date,
//	 			        		    LocalTime.parse("00:00:00"),
//	 			        		    LocalTime.parse("00:00:00"),
//	 			        		    LeaveType.ABSENT.name()
//	 			        		);
//	 	            	}
//	 	            	else {
//	 	            		AttendanceRequestDTO dto1 = new AttendanceRequestDTO();
//	 	            		dto1.setEmpId(req.getEmployee().getEmployeeId());
//	 	            		dto1.setCheckIn(LocalTime.parse("00:00:00"));
//	 	            		dto1.setCheckOut(LocalTime.parse("00:00:00"));
//	 	            		dto1.setDate(date);
//	 	            		dto1.setStatus(LeaveType.ABSENT.name());
//	 	            	    
//	 	            		leaveRecord.updateAbsent(req.getEmployee(), req.getEmployee().getEmployeeId(), dto1);
//	 	            	}
//	 		        	
//	 		        	}
//	            }
//	           
		        
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
	        
	        if (dto.getStatus() == LeaveStatus.APPROVED) {
	        if(remaining.getRemainingDays() >= 0) {
	        	req.setLeavePaid(true);
	        }
	        else {
	        	req.setLeavePaid(false);
	        }
	        }

	        LeaveRequest res = leaveRequestRepository.save(req);
	        LeaveRequestDTO dto1 = new LeaveRequestDTO();
	        
	        dto1.setId(res.getId());
	        dto1.setEmpId(req.getEmployee_Id());
	        dto1.setStartDate(res.getStartDate());
	        dto1.setEndDate(res.getEndDate());
	        dto1.setTotalDays(res.getTotalDays());
	        dto1.setLeaveTime(res.getLeaveTime());
	        dto1.setLeaveType(res.getLeaveType().getName());
	        dto1.setDepartment(res.getDepartment());
	        dto1.setApproverEmail1(res.getApproverEmail1());
	        dto1.setApproverEmail2(res.getApproverEmail2());
	        dto1.setReviewedBy(req.getReviewedBy().getEmail());
	        dto1.setReviewedAt(req.getReviewedAt());
	        dto1.setCreatedAt(req.getCreatedAt());
	        dto1.setHrRemarks(req.getHrRemarks());
	        dto1.setStatus(res.getStatus());
	        dto1.setReason(res.getReason());
	        dto1.setLeavePaid(req.isLeavePaid());
	        dto1.setHrRemarks(res.getHrRemarks());
	        return ResponseEntity.ok(dto1);

	    }
	    
	    
	    @Transactional
	    public ResponseEntity<?> reviewHalfDayLeave(String empId, Long leaveId, ReviewLeaveDto dto) {   
	    	Long id = empRepository.findIdByEmployeeId(empId);
	        Employee emp = getUserByEmployeeId(id);
	        LeaveRequest req = getLeaveById(leaveId);
	        
	        
//	        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR) {
//	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
//	        }
	        
	        if(!emp.getEmail().equalsIgnoreCase(req.getApproverEmail1()) && !emp.getEmail().equalsIgnoreCase(req.getApproverEmail2())) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized to approve this leave request. Only the designated approver (" 
	                    + req.getApproverEmail1() +" or "+req.getApproverEmail1()+") can perform this action.");
	        }
	        
	        Optional<LeaveType> leaveType = leaveTypeRepository.findByName(req.getLeaveType().getName());
	        if(leaveType.isEmpty()) {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave type not found with name: "+req.getLeaveType().getName());
	        }
	        req.setLeaveType(leaveType.get());

	        if (req.getStatus() == LeaveStatus.CANCELLED)
	            throw new BadRequestException("Cannot review a cancelled request");
	        if (req.getStatus() == LeaveStatus.APPROVED || req.getStatus() == LeaveStatus.REJECTED)
	            throw new BadRequestException("Request has already been reviewed");
	        if (dto.getStatus() != LeaveStatus.APPROVED && dto.getStatus() != LeaveStatus.REJECTED)
	            throw new BadRequestException("Status must be APPROVED or REJECTED");
	        LeaveBalance remaining = null;
	        if (dto.getStatus() == LeaveStatus.APPROVED) {

	            LocalDate current = req.getStartDate();

	            while ( current.isBefore(req.getEndDate()) ||
	            	    current.isEqual(req.getEndDate())) {

	                int month = current.getMonthValue();
	                int year = current.getYear();

	                Optional<LeaveBalance> bal = leaveBalanceRepository.findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(req.getEmployee(), year, leaveType.get(), req.getDepartment().getName());
	                
	                if (bal.isEmpty()) {

	                    throw new BadRequestException(
	                            "Leave balance not found for employee: "+req.getEmployee_Id()+"/"
	                            + req.getLeaveType() + "/" + year + "/" +  req.getDepartment()
	                    );
	                }
	                LeaveBalance balance =  bal.get();

	                

	           
	                balance.setUsedDays(
	                        balance.getUsedDays() + 0.5);
	          


	                balance.setRemainingDays(balance.getRemainingDays()); 
	                remaining = leaveBalanceRepository.save(balance);

	               
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
	        
	        if (dto.getStatus() == LeaveStatus.APPROVED) {
		        if(remaining.getRemainingDays() >= 0) {
		        	req.setLeavePaid(true);
		        }
		        else {
		        	req.setLeavePaid(false);
		        }
		    }

	        LeaveRequest res = leaveRequestRepository.save(req);
	        LeaveRequestDTO dto1 = new LeaveRequestDTO();
	        
	        dto1.setId(res.getId());
	        dto1.setEmpId(req.getEmployee_Id());
	        dto1.setStartDate(res.getStartDate());
	        dto1.setEndDate(res.getEndDate());
	        dto1.setTotalDays(res.getTotalDays());
	        dto1.setLeaveTime(res.getLeaveTime());
	        dto1.setLeaveType(res.getLeaveType().getName());
	        dto1.setDepartment(res.getDepartment());
	        dto1.setApproverEmail1(res.getApproverEmail1());
	        dto1.setApproverEmail2(res.getApproverEmail2());
	        dto1.setReviewedBy(req.getReviewedBy().getEmail());
	        dto1.setReviewedAt(req.getReviewedAt());
	        dto1.setCreatedAt(req.getCreatedAt());
	        dto1.setHrRemarks(req.getHrRemarks());
	        dto1.setStatus(res.getStatus());
	        dto1.setReason(res.getReason());
	        dto1.setLeavePaid(req.isLeavePaid());
	        dto1.setHrRemarks(res.getHrRemarks());
	        return ResponseEntity.ok(dto1);

	    }
	    
	       
	 public LeaveRequest getLeaveById(Long id) {
	        return leaveRequestRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
	    }
	 
	 
	 @Transactional
	 public ResponseEntity<?> updateLeave(
	         String empId,
	         Long leaveId,
	         LeaveRequest request) {

	     Long id = empRepository.findIdByEmployeeId(empId);

	     LeaveRequest leave = getLeaveById(leaveId);

	     if (!leave.getEmployee().getId().equals(id)) {
	         return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                 .body("You can update only your own leave request");
	     }

	     if (leave.getStatus() != LeaveStatus.PENDING) {
	         return ResponseEntity.badRequest()
	                 .body("Only pending leave requests can be updated");
	     }

	     if (request.getStartDate() != null) {
	         leave.setStartDate(request.getStartDate());
	     }

	     if (request.getEndDate() != null) {
	         leave.setEndDate(request.getEndDate());
	     }

	     if (request.getReason() != null) {
	         leave.setReason(request.getReason());
	     }
	     
        Optional<LeaveType> leaveType = leaveTypeRepository.findByName(request.getLeaveType().getName());
        if(leaveType.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave type not found with name: "+request.getLeaveType().getName());
        }
        request.setLeaveType(leaveType.get());


	     List<LeaveRequest> overlaps =
	             leaveRequestRepository.findOverlappingLeaves(
	                     id,
	                     leave.getStartDate(),
	                     leave.getEndDate());

	     overlaps.removeIf(l ->
	             l.getId().equals(leaveId));

	     if (!overlaps.isEmpty()) {
	         return ResponseEntity.badRequest()
	                 .body("Updated leave dates overlap with existing leave request");
	     }

	     LeaveRequest updated =
	             leaveRequestRepository.save(leave);
	     
	       LeaveRequestDTO dto = new LeaveRequestDTO();
	        
	        dto.setId(updated.getId());
	        dto.setEmpId(empId);
	        dto.setStartDate(updated.getStartDate());
	        dto.setEndDate(updated.getEndDate());
	        dto.setTotalDays(updated.getTotalDays());
	        dto.setLeaveTime(updated.getLeaveTime());
	        dto.setLeaveType(updated.getLeaveType().getName());
	        dto.setDepartment(updated.getDepartment());
	        dto.setApproverEmail1(updated.getApproverEmail1());
	        dto.setApproverEmail2(updated.getApproverEmail2());
	        dto.setReviewedBy(updated.getReviewedBy().getEmail());
	        dto.setReviewedAt(updated.getReviewedAt());
	        dto.setCreatedAt(updated.getCreatedAt());
	        dto.setStatus(updated.getStatus());
	        dto.setReason(updated.getReason());
	        dto.setHrRemarks(updated.getHrRemarks());
	        dto.setLeavePaid(updated.isLeavePaid());

	     return ResponseEntity.ok(updated);
	 }
	
	 public double countWorkingDays(LocalDate start, LocalDate end, boolean isHalfDay) {

		    double count = 0;

		    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
		        if (d.getDayOfWeek() != DayOfWeek.SATURDAY &&
		            d.getDayOfWeek() != DayOfWeek.SUNDAY) {

		            if (isHalfDay && start.equals(end)) {
		                count += 0.5;
		            } else {
		                count += 1;
		            }
		        }
		    }

		    return count;
		}
	
	public double countHalfDays(LocalDate start, LocalDate end) {

	    double count = 0;

	    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
	        if (d.getDayOfWeek() != DayOfWeek.SATURDAY &&
	            d.getDayOfWeek() != DayOfWeek.SUNDAY) {
	                count += 0.5;      
	        }
	    }

	    return count;
	}
	
	
	
	@Transactional
	public ResponseEntity<?> deleteLeave(
	        String empId,
	        Long leaveId) {

	    Long id = empRepository.findIdByEmployeeId(empId);

	    LeaveRequest leave = getLeaveById(leaveId);

	    if (!leave.getEmployee().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("You can delete only your own leave request");
	    }

	    if (leave.getStatus() != LeaveStatus.PENDING) {
	        return ResponseEntity.badRequest()
	                .body("Only pending leave requests can be deleted");
	    }

	    leaveRequestRepository.delete(leave);

	    return ResponseEntity.ok(
	            "Leave request deleted successfully");
	}
	
	public List<LeaveRequest> getAllLeaves() {
	    return leaveRequestRepository
	            .findAllByOrderByCreatedAtDesc();
	}
	
	
	
	
	 private Employee getUserByEmployeeId(Long empId) {
	        return empRepository.findById(empId)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + empId));
	    }

}
