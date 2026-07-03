package com.example.EMS.EmployeeRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

	Optional<Designation> findByDepartment(String name);
}
