package com.example.EMS.Entity.WeeklyCalculations;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeeklyReport {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String employeeName;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalWorkingHours;
	
	
}
