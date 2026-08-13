package com.example.EMS.Service.LeaveService;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.Entity.Employee;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.http.ResponseEntity;

public interface LeaveRecordService {

    ResponseEntity<?> markAbsent(Employee emp, LocalDate date, LocalTime checkIn, LocalTime checkOut, String status);

    ResponseEntity<?> updateAbsent(Employee emp, String empId, AttendanceRequestDTO request);

}