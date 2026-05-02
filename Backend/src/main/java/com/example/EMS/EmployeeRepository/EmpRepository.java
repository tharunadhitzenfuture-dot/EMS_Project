package com.example.EMS.EmployeeRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.EMS.EmployeeEntity.Employee;

public interface EmpRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmail(String email);
	Optional<Employee> findByEmployeeId(String employee_id);
	void deleteByEmployeeId(String emp_id);
	@Query("select max(e.id) from Employee e")
	Long findMaxId();
	
	

}
