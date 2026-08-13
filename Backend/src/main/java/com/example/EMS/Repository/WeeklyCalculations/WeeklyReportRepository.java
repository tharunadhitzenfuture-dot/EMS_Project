package com.example.EMS.Repository.WeeklyCalculations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.WeeklyCalculations.WeeklyReportDTO;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReportDTO, Long> {

}
