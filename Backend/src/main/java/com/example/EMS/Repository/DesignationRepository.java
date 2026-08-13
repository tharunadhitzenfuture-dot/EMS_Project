package com.example.EMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

	Optional<Designation> findByDepartment(String name);
}
