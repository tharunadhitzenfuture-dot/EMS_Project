package com.example.EMS.EmployeeRepository.DepartmentRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.Departments.Departments;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {
	
	Optional<Departments> findByName(String name);

}
