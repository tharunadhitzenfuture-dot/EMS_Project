package com.example.EMS.EmployeeRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.ShiftEmployeeDetails;

@Repository
public interface ShiftEmployeeDetailsRepository extends JpaRepository<ShiftEmployeeDetails, Long> {

	Optional<ShiftEmployeeDetails> findByEmpId(String empId);
	
	Optional<ShiftEmployeeDetails> findByEmpIdAndStartTimeAndEndTime(String empId, LocalDate startTime, LocalDate endTime);
	
	String findEmpIdById(Long id);
}	
