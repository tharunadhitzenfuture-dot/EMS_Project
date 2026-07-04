package com.example.EMS.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.RolesAssign;

public interface RoleAssignRepository extends JpaRepository<RolesAssign, Long> {
	
	Optional<RolesAssign> findByRole(String name);
	
	@Query("""
		    SELECT e
		    FROM Employee e
		    WHERE LOWER(e.role) = LOWER(:role)
		      AND (
		            LOWER(e.first_name) LIKE LOWER(CONCAT('%', :name, '%'))
		         OR LOWER(e.last_name) LIKE LOWER(CONCAT('%', :name, '%'))
		      )
		""")
		List<Employee> searchEmployee(@Param("role") String department,
		                              @Param("name") String name);

}
