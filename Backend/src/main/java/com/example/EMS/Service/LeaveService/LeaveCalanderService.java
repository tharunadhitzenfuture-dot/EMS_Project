package com.example.EMS.Service.LeaveService;

import com.example.EMS.Entity.HolidayCalander;
import org.springframework.http.ResponseEntity;

public interface LeaveCalanderService {

    ResponseEntity<?> createCalander(HolidayCalander request);

    ResponseEntity<?> getAllCalanders();

    ResponseEntity<?> getCalanderById(Long id);

    ResponseEntity<?> deleteCalanderById(Long id);

    ResponseEntity<?> updateCalanderById(Long id, HolidayCalander request);

}