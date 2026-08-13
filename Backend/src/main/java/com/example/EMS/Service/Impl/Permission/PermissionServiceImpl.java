package com.example.EMS.Service.Impl.Permission;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.EMS.Service.Attendance.AttendanceService;
import com.example.EMS.Service.Permission.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.LeaveEntity.Permission;
import com.example.EMS.Exception.BadRequestException;
import com.example.EMS.Exception.ResourceNotFoundException;
import com.example.EMS.Repository.AttendanceRepository;
import com.example.EMS.Repository.EmpRepository;
import com.example.EMS.Repository.LeaveRepository.PermissionRepository;
import com.example.EMS.enums.LeaveStatus;


@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
	
	private final EmpRepository empRepository;
	private final PermissionRepository permissionRepo;
	private final AttendanceRepository attendanceRepo;
	private final AttendanceService attendanceService;

	public ResponseEntity<?> applyPermission(String empId, Permission permission){
		Long id = empRepository.findIdByEmployeeId(empId);
		Employee emp = getUserByEmployeeId(id);
		

		 
//        if (permission.getEndDate().isBefore(permission.getStartDate()))
//            throw new BadRequestException("End date must be on or after start date");
        String email1 = emp.getApproval().getApproverEmail1();
        String email2 = emp.getApproval().getApproverEmail2();
        
        if(email1 == null || email1.isBlank()) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Approver 1 not set");
		}
        
        permission.setEmployee(emp);
        permission.setApproverEmail1(email1);
        permission.setApproverEmail2(email2);
        Permission res = permissionRepo.save(permission);
        return ResponseEntity.ok(res);

	}
	
	
    public ResponseEntity<?> reviewPermission(String empId, Long permissionId, Permission dto) {   
    	Long id = empRepository.findIdByEmployeeId(empId);
        Employee emp = getUserByEmployeeId(id);
        Permission req = getPermissionById(permissionId);
        
        
//        if(emp.getRole() != Role.MANAGER && emp.getRole() != Role.HR && emp.getRole() != Role.ADMIN) {
//        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not authorized to review leave requests");
//        }
//        
        if(!emp.getEmail().equals(req.getApproverEmail1()) && !emp.getEmail().equals(req.getApproverEmail2())) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("You are not authorized to approve this leave request. Only the designated approver (" 
                    + req.getApproverEmail1() +" or "+req.getApproverEmail1()+") can perform this action.");
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
        
 //       LocalDate permissionDate = req.getPermissionDate();
 //      Optional<Attendance> attendance =  attendanceRepo.findByEmployee_EmployeeIdAndAttendanceDate(empId,permissionDate);
        
//        if(attendance.isEmpty()) {
//        	attendanceService.registerService(emp, permissionDate, null, null, LeaveType.PERMISSION.name());
//        }
//        else {
//        	attendance.get().setStatus(LeaveType.PERMISSION);
//        }
        
        
        
        
        

        Permission res = permissionRepo.save(req);
        return ResponseEntity.ok(res);

    }
    
    public ResponseEntity<?> updatePermission(
            String empId,
            Long permissionId,
            Permission request) {

        Long id = empRepository.findIdByEmployeeId(empId);

        Permission permission = getPermissionById(permissionId);

        if (!permission.getEmployee().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can update only your own permission request");
        }

        if (permission.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.badRequest()
                    .body("Only pending permission requests can be updated");
        }

        if (request.getReason() != null) {
            permission.setReason(request.getReason());
        }

        if (request.getHours() != null) {
            permission.setHours(request.getHours());
        }

        if (request.getPermissionDate() != null) {

            Optional<Permission> existing =
                    permissionRepo.findByPermissionDateAndEmployee_Id(
                            request.getPermissionDate(),
                            id
                    );

            if (existing.isPresent()
                    && !existing.get().getId().equals(permissionId)) {

                return ResponseEntity.badRequest()
                        .body("Permission already applied for date: "
                                + request.getPermissionDate());
            }

            permission.setPermissionDate(
                    request.getPermissionDate()
            );
        }

        Permission updated = permissionRepo.save(permission);

        return ResponseEntity.ok(updated);
    }
    
    public ResponseEntity<?> deletePermission(
            String empId,
            Long permissionId) {

        Long id = empRepository.findIdByEmployeeId(empId);

        Permission permission = getPermissionById(permissionId);

        if (!permission.getEmployee().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can delete only your own permission request");
        }

        if (permission.getStatus() != LeaveStatus.PENDING) {
            return ResponseEntity.badRequest()
                    .body("Only pending permission requests can be deleted");
        }

        permissionRepo.delete(permission);

        return ResponseEntity.ok(
                "Permission request deleted successfully"
        );
    }
    
    public List<Permission> getAllPermission(){
    	return permissionRepo.findAll();
    }
    
    
    
	
	private Employee getUserByEmployeeId(Long empId) {
        return empRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + empId));
    }

	public Permission getPermissionById(Long id) {
        return permissionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission request not found: " + id));
    }
	
	public List<Permission> getListPermissionById(Long empId) {
	    List<Permission> permissions = permissionRepo.findByEmployeeId(empId);

	    if (permissions.isEmpty()) {
	        throw new ResourceNotFoundException(
	                "No permission requests found for employee: " + empId);
	    }

	    return permissions;
	}
}
