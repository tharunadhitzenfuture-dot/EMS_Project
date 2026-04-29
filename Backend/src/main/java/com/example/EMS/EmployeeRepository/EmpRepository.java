package com.example.EMS.EmployeeRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.Employee;

public interface EmpRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmail(String email);
	Optional<Employee> findByEmployeeId(Long employee_id);
	
	

}
