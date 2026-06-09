package com.example.EMS.EmployeeService.LeaveService;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeService.AttendanceService;

@Service
public class LeaveRecordService {

	private final AttendanceService attendanceService;

	 public LeaveRecordService(@Lazy AttendanceService attendanceService) {
	        this.attendanceService = attendanceService;
	    }
	
	public ResponseEntity<?> markAbsent(Employee emp,LocalDate date, LocalTime checkIn,LocalTime checkOut, String status){
		return attendanceService.registerService(emp, date, checkIn, checkOut, status);
	}
	
	public ResponseEntity<?> updateAbsent(Employee emp,
            String empId,
            AttendanceRequestDTO request){
		return attendanceService.updateAttendance(emp, empId, request);
	}
	
	
	
}
