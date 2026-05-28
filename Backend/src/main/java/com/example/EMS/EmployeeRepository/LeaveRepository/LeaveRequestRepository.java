package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{

	List<LeaveRequest> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);
    List<LeaveRequest> findAllByOrderByCreatedAtDesc();
    
    
}
