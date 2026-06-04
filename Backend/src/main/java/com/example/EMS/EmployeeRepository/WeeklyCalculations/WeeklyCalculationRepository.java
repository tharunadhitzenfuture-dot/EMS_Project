package com.example.EMS.EmployeeRepository.WeeklyCalculations;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;

public interface WeeklyCalculationRepository extends JpaRepository<WeeklyCalculation, Long> {

	Optional<WeeklyCalculation> findByDeptName(String name);
	
	
}
