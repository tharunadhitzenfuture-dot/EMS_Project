package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
	List<Permission> findByEmployeeIdAndStartDateAndEndDate(
	        Long empId,
	        LocalDate startDate,
	        LocalDate endDate);
	
	Optional<Permission> findByPermissionDateAndEmployeeId(LocalDate date,Long id);

}
