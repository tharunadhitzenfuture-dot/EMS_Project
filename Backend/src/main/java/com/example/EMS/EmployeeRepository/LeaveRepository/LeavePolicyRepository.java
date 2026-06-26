package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.enums.Department;
import com.example.EMS.enums.LeaveType;



@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy,Long>{
	
	Optional<LeavePolicy> findByMonthAndYear(Integer month, Integer year);
	Optional<LeavePolicy> findByYear(Integer year);
	Optional<LeavePolicy> findByYearAndType(Integer year, LeaveType type);
	Optional<LeavePolicy> findByYearAndTypeAndDepartment(Integer year, LeaveType type, Department department);
	Optional<List<LeavePolicy>> findByDepartment(Department department);

}
