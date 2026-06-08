package com.example.EMS.EmployeeController.LeaveController;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EMS.EmployeeEntity.HolidayCalander;
import com.example.EMS.EmployeeRepository.LeaveRepository.LeaveCalanderRepository;
import com.example.EMS.EmployeeService.LeaveService.LeaveCalanderService;

@RestController
@RequestMapping("/api/leaveCalander")
public class HolidayCalanderController {

    private final LeaveCalanderRepository calanderRepo;
    private final LeaveCalanderService calanderService;

    public HolidayCalanderController(
            LeaveCalanderRepository calanderRepo,
            LeaveCalanderService calanderService) {
        this.calanderRepo = calanderRepo;
        this.calanderService = calanderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLeaveCalander(
            @RequestBody HolidayCalander request) {

        Optional<HolidayCalander> cal =
                calanderRepo.findByDate(request.getDate());

        if (cal.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Date : " + request.getDate()
                            + " already registered with "
                            + cal.get().getReason());
        }

        return calanderService.createCalander(request);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCalanders() {
        return calanderService.getAllCalanders();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCalanderById(
            @PathVariable Long id) {
        return calanderService.getCalanderById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCalanderById(
            @PathVariable Long id) {
        return calanderService.deleteCalanderById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCalanderById(
            @PathVariable Long id,
            @RequestBody HolidayCalander request) {
        return calanderService.updateCalanderById(id, request);
    }
}