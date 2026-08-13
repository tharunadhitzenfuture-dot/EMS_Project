package com.example.EMS.Service.WeeklyCalculations;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface WeeklyReportService {

    ResponseEntity<?> getWeeklyReport(List<String> ids);

    long timeToSeconds(String time);

    String secondsToTime(long totalSeconds);

    String getPermissionHours(String empId, LocalDate start, LocalDate end);

    String getByDepartment(String dept);

    Long getHours(String hour);

}