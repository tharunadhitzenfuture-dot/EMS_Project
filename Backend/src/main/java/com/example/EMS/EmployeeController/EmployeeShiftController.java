package com.example.EMS.EmployeeController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EMS.EmployeeEntity.EmployeeShift;
import com.example.EMS.EmployeeRepository.ShiftTimingRepository;
import com.example.EMS.EmployeeService.ShiftTimingService;

@RestController
@RequestMapping("/api/shift")
public class EmployeeShiftController {


	private ShiftTimingService shiftService;
	private ShiftTimingRepository shiftRepository;
	
	public EmployeeShiftController(ShiftTimingService shiftService, ShiftTimingRepository shiftRepository) {
		this.shiftService = shiftService;
		this.shiftRepository = shiftRepository;
	}

	@PostMapping("/addShift")
	public ResponseEntity<?> registerShift(@RequestBody EmployeeShift shift){
		
		if(shift.getShiftCode() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift code");
		}
		
		if(shift.getShiftName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift name");
		}
		if(shift.getShiftType() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift type");
		}
		if(shift.getStartTime() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift start time");
		}
		if(shift.getEndTime() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter shift end time");
		}
	
		return shiftService.createShift(shift);
		
	}
	
	@GetMapping("/getAllShift")
	public ResponseEntity<?> getAllShift() {
	    return shiftService.getAllShift();
	}

	@GetMapping("/getShift/{code}")
	public ResponseEntity<?> getShiftById(@PathVariable String code) {
		if (code == null) {
	        return ResponseEntity.badRequest().body("Please enter shift code");
	    }
		Long id = findIdByCode(code);
	    return shiftService.getShiftById(id);
	}

	@PatchMapping("/updateShift/{code}")
	public ResponseEntity<?> updateShift(@PathVariable String code,
	                                     @RequestBody EmployeeShift shift) {
			
	    if (code == null) {
	        return ResponseEntity.badRequest().body("Please enter shift code");
	    }
	    
	    Long id = findIdByCode(code);

	    if (shift.getShiftName() == null) {
	        return ResponseEntity.badRequest().body("Please enter shift name");
	    }

	    if (shift.getShiftType() == null) {
	        return ResponseEntity.badRequest().body("Please enter shift type");
	    }

	    if (shift.getStartTime() == null) {
	        return ResponseEntity.badRequest().body("Please enter shift start time");
	    }

	    if (shift.getEndTime() == null) {
	        return ResponseEntity.badRequest().body("Please enter shift end time");
	    }

	    return shiftService.updateShift(id, shift);
	}

	@DeleteMapping("/deleteShift/{code}")
	public ResponseEntity<?> deleteShift(@PathVariable String code) {
		Long id = findIdByCode(code);
	    return shiftService.deleteShift(id);
	}
	
	
	public Long findIdByCode(String code) {
		return shiftRepository.findByShiftCode(code)
						.map(EmployeeShift::getId)
						.orElseThrow(()-> new RuntimeException("Shift not found with code: " + code));
	}
	
}
