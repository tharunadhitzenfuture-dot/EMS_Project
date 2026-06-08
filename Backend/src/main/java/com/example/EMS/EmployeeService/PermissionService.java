package com.example.EMS.EmployeeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.EMS.EmployeeEntity.Attendance;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;
import com.example.EMS.EmployeeException.BadRequestException;
import com.example.EMS.EmployeeException.ResourceNotFoundException;
import com.example.EMS.EmployeeRepository.AttendanceRepository;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.LeaveRepository.PermissionRepository;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveType;
import com.example.EMS.enums.Role;

@Service
public class PermissionService {
	
	private final EmpRepository empRepository;
	private final PermissionRepository permissionRepo;
	private final AttendanceRepository attendanceRepo;
	
	
	

	public PermissionService(EmpRepository empRepository, PermissionRepository permissionRepo,
			AttendanceRepository attendanceRepo) {
		this.empRepository = empRepository;
		this.permissionRepo = permissionRepo;
		this.attendanceRepo = attendanceRepo;
	}


	public ResponseEntity<?> applyPermission(String empId, Permission permission){
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		

		 
//        if (permission.getEndDate().isBefore(permission.getStartDate()))
//            throw new BadRequestException("End date must be on or after start date");
        
        
        permission.setEmployee(emp);
        Permission res = permissionRepo.save(permission);
        return ResponseEntity.ok(res);
		
        
		
	}
	
	
    public ResponseEntity<?> reviewPermission(String empId, Long permissionId, Permission dto) {   
    	Long id = empRepository.findIdByEmployeeId(empId);
        Employee emp = getUserByEmployeeId(id);
        Permission req = getPermissionById(permissionId);
        
        
        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
        }
        
        

        if (req.getStatus() == LeaveStatus.CANCELLED)
            throw new BadRequestException("Cannot review a cancelled request");
        if (req.getStatus() == LeaveStatus.APPROVED || req.getStatus() == LeaveStatus.REJECTED)
            throw new BadRequestException("Request has already been reviewed");
        if (dto.getStatus() != LeaveStatus.APPROVED && dto.getStatus() != LeaveStatus.REJECTED)
            throw new BadRequestException("Status must be APPROVED or REJECTED");
       
        
        req.setStatus(dto.getStatus());
        req.setHrRemarks(dto.getHrRemarks());
        req.setReviewedBy(emp);
        req.setReviewedAt(LocalDateTime.now());
        
        LocalDate permissionDate = req.getPermissionDate();
        Optional<Attendance> attendance =  attendanceRepo.findByAttendanceDate(permissionDate);
        
        if(attendance.isEmpty()) {
        	return ResponseEntity.badRequest().body("Attendance not recorded for date: "+permissionDate);
        }
        
        attendance.get().setStatus(LeaveType.PERMISSION);

        Permission res = permissionRepo.save(req);
        return ResponseEntity.ok(res);

    }
    
	
	private Employee getUserByEmployeeId(Long empId) {
        return empRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + empId));
    }

	private Permission getPermissionById(Long id) {
        return permissionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
    }
}
