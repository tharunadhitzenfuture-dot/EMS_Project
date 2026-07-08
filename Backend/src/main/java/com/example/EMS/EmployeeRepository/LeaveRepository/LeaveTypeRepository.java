package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>{
	
	Optional<LeaveType> findByName(String name);
}
