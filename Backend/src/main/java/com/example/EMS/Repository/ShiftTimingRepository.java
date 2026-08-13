package com.example.EMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.EmployeeShift;

public interface ShiftTimingRepository extends JpaRepository<EmployeeShift,Long>{

	Optional<EmployeeShift> findByShiftCode(String code);
	Optional<EmployeeShift> findByShiftName(String name);
}
