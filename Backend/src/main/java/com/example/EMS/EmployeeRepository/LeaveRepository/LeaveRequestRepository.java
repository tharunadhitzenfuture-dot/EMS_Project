package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EMS.EmployeeEntity.ApprovalSystem;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.example.EMS.enums.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{

	List<LeaveRequest> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
    List<LeaveRequest> findAllByOrderByCreatedAtDesc();
    
    @Query("""
    	    SELECT lr
    	    FROM LeaveRequest lr
    	    WHERE lr.employee.id = :empId
    	      AND lr.status <> 'REJECTED'
    	      AND lr.startDate <= :endDate
    	      AND lr.endDate >= :startDate
    	""")
    	List<LeaveRequest> findOverlappingLeaves(
    	        @Param("empId") Long empId,
    	        @Param("startDate") LocalDate startDate,
    	        @Param("endDate") LocalDate endDate);
    
    
    @Query("""
    	    SELECT lr
    	    FROM LeaveRequest lr
    	    WHERE lr.employee.id = :empId
    	      AND lr.status <> 'REJECTED'
    	      AND :date BETWEEN lr.startDate AND lr.endDate
    	""")
    	List<LeaveRequest> findLeavesContainingDate(
    	        @org.springframework.data.repository.query.Param("empId") Long empId,
    	        @org.springframework.data.repository.query.Param("date") LocalDate date);
    
    @Query("""
    	       SELECT l
    	       FROM LeaveRequest l
    	       WHERE (l.approverEmail1 = :email
    	              OR l.approverEmail2 = :email)
    	         AND l.status = :status
    	       """)
    	List<LeaveRequest> findByApproverEmailAndStatus(
    	        @Param("email") String email,
    	        @Param("status") LeaveStatus status);
    
}
