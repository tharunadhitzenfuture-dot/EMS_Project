package com.example.EMS.Controller.Leave;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EMS.Entity.HolidayCalander;
import com.example.EMS.Repository.LeaveRepository.LeaveCalanderRepository;
import com.example.EMS.Service.LeaveService.LeaveCalanderService;

@RestController
@RequestMapping("/api/leaveCalander")
@RequiredArgsConstructor
public class HolidayCalanderController {

    private final LeaveCalanderRepository calanderRepo;
    private final LeaveCalanderService calanderService;


    @PostMapping("/create")
    public ResponseEntity<?> createLeaveCalander(
            @RequestBody HolidayCalander request) {
    	
    	if(request.getDate() == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter holiday date");
    	}
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
    	
    	if(request.getDate() == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter holiday date");
    	}
    	
        return calanderService.updateCalanderById(id, request);
    }
}