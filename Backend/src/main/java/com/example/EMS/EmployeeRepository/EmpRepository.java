package com.example.EMS.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.enums.Department;

public interface EmpRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmail(String email);
	Optional<Employee> findByUser(User user); 
	Optional<Employee> findByEmployeeId(String employee_id);
	Optional<Employee> findByEmployeeIdAndEmail(String empId, String email);
	void deleteByEmployeeId(String emp_id);
	@Query("select max(e.id) from Employee e")
	Long findMaxId();
	
	@Query("""
		       select e.id
		       from Employee e
		       where e.employeeId = :employeeId
		       """)
	Long findIdByEmployeeId(
		        @Param("employeeId")
		        String employeeId);
	

	@Query("""
		    SELECT e
		    FROM Employee e
		    WHERE e.professional_details.professional_department = :department
		    """)
	List<Employee> findByProfessional_detailsProfessional_department(String department);

}
