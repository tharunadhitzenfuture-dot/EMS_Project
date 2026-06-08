package com.example.EMS.EmployeeService.LeaveService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.HolidayCalander;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveCalanderRepository;

@Service
public class LeaveCalanderService {

    private final LeaveCalanderRepository calanderRepo;

    public LeaveCalanderService(LeaveCalanderRepository calanderRepo) {
        this.calanderRepo = calanderRepo;
    }

    // Create
    public ResponseEntity<?> createCalander(HolidayCalander request) {
        HolidayCalander res = calanderRepo.save(request);
        return ResponseEntity.ok(res);
    }

    // Get All
    public ResponseEntity<?> getAllCalanders() {
        List<HolidayCalander> list = calanderRepo.findAll();
        return ResponseEntity.ok(list);
    }

    // Get By Id
    public ResponseEntity<?> getCalanderById(Long id) {
        Optional<HolidayCalander> cal = calanderRepo.findById(id);

        if (cal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Holiday Calendar not found");
        }

        return ResponseEntity.ok(cal.get());
    }

    // Delete By Id
    public ResponseEntity<?> deleteCalanderById(Long id) {
        Optional<HolidayCalander> cal = calanderRepo.findById(id);

        if (cal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Holiday Calendar not found");
        }

        calanderRepo.deleteById(id);

        return ResponseEntity.ok("Deleted Successfully");
    }

    // Update By Id
    public ResponseEntity<?> updateCalanderById(Long id, HolidayCalander request) {

        Optional<HolidayCalander> existing = calanderRepo.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Holiday Calendar not found");
        }
        
        Optional<HolidayCalander> existingDate =
                calanderRepo.findByDate(request.getDate());

        if (existingDate.isPresent()
                && !existingDate.get().getId().equals(id)) {

            return ResponseEntity.badRequest()
                    .body("Date already exists");
        }

        HolidayCalander cal = existing.get();

        cal.setDate(request.getDate());
        cal.setYear(request.getYear());
        cal.setMonth(request.getMonth());
        cal.setReason(request.getReason());

        HolidayCalander updated = calanderRepo.save(cal);

        return ResponseEntity.ok(updated);
    }
}