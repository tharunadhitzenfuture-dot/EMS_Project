package com.example.EMS.EmployeeService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.EMS.EmployeeEntity.EmployeeShift;
import com.example.EMS.EmployeeRepository.ShiftTimingRepository;

@Service
public class ShiftTimingService {

	private ShiftTimingRepository shiftRepository;

	public ShiftTimingService(ShiftTimingRepository shiftRepository) {
		this.shiftRepository = shiftRepository;
	}
	
	public ResponseEntity<?> createShift(EmployeeShift shift){
		
		Optional<EmployeeShift> sft =  shiftRepository.findByShiftCode(shift.getShiftCode());
		if(sft.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Shift code already presented");
		}
		
		Duration duration = Duration.between(shift.getStartTime(), shift.getEndTime());

		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();
		long seconds = duration.toSecondsPart();

		String workingHours = String.format("%02d:%02d:%02d", hours, minutes, seconds);

		shift.setTotalHours(workingHours);
		
		EmployeeShift res =  shiftRepository.save(shift);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> getAllShift() {

	    List<EmployeeShift> list = shiftRepository.findAll();

	    if (list.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("No shifts found");
	    }

	    return ResponseEntity.ok(list);
	}
	
	public ResponseEntity<?> getShiftById(Long id) {

	    Optional<EmployeeShift> shift = shiftRepository.findById(id);

	    if (shift.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Shift not found");
	    }

	    return ResponseEntity.ok(shift.get());
	}
	
	public ResponseEntity<?> updateShift(Long id, EmployeeShift shift) {

	    Optional<EmployeeShift> existing = shiftRepository.findById(id);

	    if (existing.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Shift not found");
	    }

	    EmployeeShift update = existing.get();


	    if(shift.getShiftName() != null && !shift.getShiftName().isBlank()) {
	    	update.setShiftName(shift.getShiftName());
	    }    
	    
	    if (shift.getShiftType() != null && !shift.getShiftType().isBlank()) {
	        update.setShiftType(shift.getShiftType());
	    }

	    if (shift.getStartTime() != null) {
	        update.setStartTime(shift.getStartTime());
	    }

	    if (shift.getEndTime() != null) {
	        update.setEndTime(shift.getEndTime());
	    }
	    //update.setActive(shift.isActive());
	    if (shift.getStartTime() != null && shift.getEndTime() != null) {
	    	if (!shift.getStartTime().isBefore(shift.getEndTime())) {
			    return ResponseEntity.badRequest()
			            .body("Start time must be before end time");
			}
	    	
	    	Duration duration = Duration.between(shift.getStartTime(), shift.getEndTime());
	    	
	    	
			long hours = duration.toHours();
			long minutes = duration.toMinutesPart();
			long seconds = duration.toSecondsPart();

			String workingHours = String.format("%02d:%02d:%02d", hours, minutes, seconds);

			update.setTotalHours(workingHours);
	    	
	    }
	    

	    EmployeeShift res = shiftRepository.save(update);

	    return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> deleteShift(Long id) {

	    Optional<EmployeeShift> shift = shiftRepository.findById(id);

	    if (shift.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Shift not found");
	    }

	    shiftRepository.deleteById(id);


	    return ResponseEntity.ok("Shift deleted successfully");
	}
	
	
}
