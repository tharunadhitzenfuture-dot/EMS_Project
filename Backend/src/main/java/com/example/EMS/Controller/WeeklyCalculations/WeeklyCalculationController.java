package com.example.EMS.Controller.WeeklyCalculations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeDTO.WorkingHoursDTO;
import com.example.EMS.Entity.WeeklyCalculations.WeeklyCalculation;
import com.example.EMS.Service.WeeklyCalculations.WeeklyCalculationService;

@RestController
@RequestMapping("/api/weekly")
@RequiredArgsConstructor
public class WeeklyCalculationController {
	
	private final WeeklyCalculationService calcService;

	@PostMapping("/create")
	public ResponseEntity<?> createWeeklyRecord(
        @RequestBody WeeklyCalculation request) {

    if (request.getDeptName() == null ||
        request.getDeptName().trim().isEmpty()) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Please enter department name");
    }

    if (request.getTotalWorkDays() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Please enter total work days");
    }

    // Calculate totalWorkHours if not provided
    if (request.getTotalWorkHours() == null ||
        request.getTotalWorkHours().trim().isEmpty()) {

        if (request.getWorkHours() != null &&
            !request.getWorkHours().trim().isEmpty()) {

            try {

                String[] parts = request.getWorkHours().split(":");

                if (parts.length != 3) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("workHours must be in HH:mm:ss format");
                }

                long hours = Long.parseLong(parts[0]);
                long minutes = Long.parseLong(parts[1]);
                long seconds = Long.parseLong(parts[2]);

                long totalSecondsPerDay =
                        (hours * 3600) + (minutes * 60) + seconds;

                long totalSeconds =
                        totalSecondsPerDay * request.getTotalWorkDays();

                long totalHours = totalSeconds / 3600;
                long remainingSeconds = totalSeconds % 3600;
                long totalMinutes = remainingSeconds / 60;
                long finalSeconds = remainingSeconds % 60;

                String totalWorkHours = String.format(
                        "%02d:%02d:%02d",
                        totalHours,
                        totalMinutes,
                        finalSeconds
                );

                request.setTotalWorkHours(totalWorkHours);

            } catch (NumberFormatException e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid workHours format. Use HH:mm:ss");
            }

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please enter total work hours or work hours per day");
        }
    }

    return calcService.createWeeklyRecord(request);
}
	
	
	
	@PostMapping("/getHours")
	public ResponseEntity<?> getWeeklyTotalHours(@RequestBody WorkingHoursDTO request){
		
		if (request.getDeptName() == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body("Please enter department name");
		}

       
		return calcService.getWeeklyHours(request);
	}
}
