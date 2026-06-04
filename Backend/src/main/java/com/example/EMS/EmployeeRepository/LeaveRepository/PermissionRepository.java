package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
	Optional<Permission> findByEmployeeIdAndStartDateAndEndDate(
	        Long empId,
	        LocalDate startDate,
	        LocalDate endDate);

}
