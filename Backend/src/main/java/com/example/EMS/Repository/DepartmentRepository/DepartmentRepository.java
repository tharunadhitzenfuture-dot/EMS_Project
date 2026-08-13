package com.example.EMS.Repository.DepartmentRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.Departments.Departments;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {
	
	Optional<Departments> findByName(String name);
	

}
