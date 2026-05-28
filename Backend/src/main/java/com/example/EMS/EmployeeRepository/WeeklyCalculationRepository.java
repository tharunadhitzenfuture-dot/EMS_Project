package com.example.EMS.EmployeeRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyCalculation;

public interface WeeklyCalculationRepository extends JpaRepository<WeeklyCalculation, Long> {

	Optional<WeeklyCalculation> findByStartDate(LocalDate date);
	Optional<WeeklyCalculation> findByEndDate(LocalDate date);
	
	
}
