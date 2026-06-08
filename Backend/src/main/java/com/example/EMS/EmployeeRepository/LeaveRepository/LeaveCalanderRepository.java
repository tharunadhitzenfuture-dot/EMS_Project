package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.HolidayCalander;

@Repository
public interface LeaveCalanderRepository extends JpaRepository<HolidayCalander, Long>{
	
	Optional<HolidayCalander> findByDate(LocalDate date);

}
