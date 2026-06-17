package com.example.EMS.EmployeeService;

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

	    Optional<EmployeeShift> shiftCode = shiftRepository.findByShiftCode(shift.getShiftCode());

	    if (shiftCode.isPresent() && !shiftCode.get().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Shift code already exists");
	    }

	    update.setShiftCode(shift.getShiftCode());
	    update.setShiftName(shift.getShiftName());
	    update.setShiftType(shift.getShiftType());
	    update.setStartTime(shift.getStartTime());
	    update.setEndTime(shift.getEndTime());
	    update.setActive(shift.isActive());

	    EmployeeShift res = shiftRepository.save(update);

	    return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<?> deleteShift(Long id) {

	    Optional<EmployeeShift> shift = shiftRepository.findById(id);

	    if (shift.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Shift not found");
	    }

	    EmployeeShift delete = shift.get();
	    delete.setActive(false);

	    shiftRepository.save(delete);

	    return ResponseEntity.ok("Shift deleted successfully");
	}
	
	
}
