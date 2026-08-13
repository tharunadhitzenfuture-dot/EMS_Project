package com.example.EMS.Service.Impl.LeaveService;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.EMS.Service.LeaveService.LeaveRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.AttendanceRequestDTO;
import com.example.EMS.Entity.Employee;
import com.example.EMS.Service.Attendance.AttendanceService;

@Service
@AllArgsConstructor
public class LeaveRecordServiceImpl implements LeaveRecordService {

	private final AttendanceService attendanceService;

	public ResponseEntity<?> markAbsent(Employee emp,LocalDate date, LocalTime checkIn,LocalTime checkOut, String status){
		return attendanceService.registerService(emp, date, checkIn, checkOut, status);
	}
	
	public ResponseEntity<?> updateAbsent(Employee emp,
            String empId,
            AttendanceRequestDTO request){
		return attendanceService.updateAttendance(emp, empId, request);
	}
	
	
	
}
