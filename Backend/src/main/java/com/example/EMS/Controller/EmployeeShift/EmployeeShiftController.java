package com.example.EMS.Controller.EmployeeShift;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.Entity.EmployeeShift;
import com.example.EMS.Repository.ShiftTimingRepository;
import com.example.EMS.Service.ShiftTiming.ShiftTimingService;

@RestController
@RequestMapping("/api/shift")
@RequiredArgsConstructor
public class EmployeeShiftController {

	private final ShiftTimingService shiftService;
	private final ShiftTimingRepository shiftRepository;

	@PostMapping("/addShift")
	public ResponseEntity<?> registerShift(@RequestBody EmployeeShift shift){
		
		if(shift.getShiftCode() == null || shift.getShiftCode().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift code");
		}
		
		if(shift.getShiftName() == null || shift.getShiftName().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift name");
		}
//		if(shift.getShiftType() == null ||  shift.getShiftType().isBlank()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift type");
//		}
		if(shift.getStartTime() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift start time");
		}
		if(shift.getEndTime() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift end time");
		}	
		if (!shift.getStartTime().isBefore(shift.getEndTime())) {
		    return ResponseEntity.badRequest()
		            .body("Start time must be before end time");
		}
		return shiftService.createShift(shift);
		
	}
	
	@GetMapping("/getAllShift")
	public ResponseEntity<?> getAllShift() {
	    return shiftService.getAllShift();
	}

	@GetMapping("/getShift/{id}")
	public ResponseEntity<?> getShiftById(@PathVariable Long id) {
		if (id == null) {
	        return ResponseEntity.badRequest().body("Please enter id");
	    }
	    return shiftService.getShiftById(id);
	}

	@PatchMapping("/updateShift/{id}")
	public ResponseEntity<?> updateShift(@PathVariable Long id,
	                                     @RequestBody EmployeeShift shift) {
			
	    if (id == null) {
	        return ResponseEntity.badRequest().body("Please enter Id");
	    }


	    return shiftService.updateShift(id, shift);
	}

	@DeleteMapping("/deleteShift/{id}")
	public ResponseEntity<?> deleteShift(@PathVariable Long id) {
	    return shiftService.deleteShift(id);
	}
	
	
	public Long findIdByCode(String code) {
		return shiftRepository.findByShiftCode(code)
						.map(EmployeeShift::getId)
						.orElseThrow(()-> new RuntimeException("Shift not found with code: " + code));
	}
	
}
