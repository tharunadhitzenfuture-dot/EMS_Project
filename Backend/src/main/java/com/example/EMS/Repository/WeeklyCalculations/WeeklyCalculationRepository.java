package com.example.EMS.Repository.WeeklyCalculations;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.WeeklyCalculations.WeeklyCalculation;

public interface WeeklyCalculationRepository extends JpaRepository<WeeklyCalculation, Long> {

	Optional<WeeklyCalculation> findByDeptName(String name);
	
	
}
