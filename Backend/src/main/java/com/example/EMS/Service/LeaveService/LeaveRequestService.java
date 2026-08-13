package com.example.EMS.Service.LeaveService;

import com.example.EMS.EmployeeDTO.ReviewLeaveDto;
import com.example.EMS.Entity.LeaveEntity.LeaveRequest;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface LeaveRequestService {

    ResponseEntity<?> handleReviewLeave(String empId, Long leaveId, ReviewLeaveDto dto);

    ResponseEntity<?> applyLeave(String empId, LeaveRequest request);

    ResponseEntity<?> applyEmpLeave(LeaveRequest request);

    ResponseEntity<?> reviewLeave(String empId, Long leaveId, ReviewLeaveDto dto);

    ResponseEntity<?> reviewHalfDayLeave(String empId, Long leaveId, ReviewLeaveDto dto);

    LeaveRequest getLeaveById(Long id);

    ResponseEntity<?> updateLeave(String empId, Long leaveId, LeaveRequest request);

    double countWorkingDays(LocalDate start, LocalDate end, boolean isHalfDay);

    double countHalfDays(LocalDate start, LocalDate end);

    ResponseEntity<?> deleteLeave(String empId, Long leaveId);

    List<LeaveRequest> getAllLeaves();

}