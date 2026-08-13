package com.example.EMS.Service.ShiftTiming;

import com.example.EMS.Entity.EmployeeShift;
import org.springframework.http.ResponseEntity;

public interface ShiftTimingService {

    ResponseEntity<?> createShift(EmployeeShift shift);

    ResponseEntity<?> getAllShift();

    ResponseEntity<?> getShiftById(Long id);

    ResponseEntity<?> updateShift(Long id, EmployeeShift shift);

    ResponseEntity<?> deleteShift(Long id);

}