package com.example.EMS.Service.Attendance;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.Entity.Employee;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {

    Long getIdByEmployeeId(String empId);

    ResponseEntity<?> registerService(Employee emp, LocalDate date, LocalTime checkIn, LocalTime checkOut, String status);

    ResponseEntity<?> getAllAttendance();

    ResponseEntity<?> getAttendanceById(String empId);

    ResponseEntity<?> deleteAttendanceById(String empId);

    ResponseEntity<?> updateAttendance(Employee emp, String empId, AttendanceRequestDTO request);

    ResponseEntity<?> getWeeklyHours(WorkingHoursDTO request);

    ResponseEntity<?> getEmpPresent(LocalDate date);

    ResponseEntity<?> calculateWeeklyHours(String empId);

}