package com.example.EMS.EmployeeRepository.WeeklyCalculations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.WeeklyCalculations.WeeklyReportDTO;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReportDTO, Long> {

}
