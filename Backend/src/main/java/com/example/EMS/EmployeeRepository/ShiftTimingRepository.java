package com.example.EMS.EmployeeRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.EmployeeShift;

public interface ShiftTimingRepository extends JpaRepository<EmployeeShift,Long>{

	Optional<EmployeeShift> findByShiftCode(String code);
	Optional<EmployeeShift> findByShiftName(String name);
}
