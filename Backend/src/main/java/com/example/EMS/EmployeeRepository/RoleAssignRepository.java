package com.example.EMS.EmployeeRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.RolesAssign;

public interface RoleAssignRepository extends JpaRepository<RolesAssign, Long> {
	
	Optional<RolesAssign> findByRole(String name);

}
