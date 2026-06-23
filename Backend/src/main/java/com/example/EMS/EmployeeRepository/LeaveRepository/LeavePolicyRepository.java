package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.enums.LeaveType;



@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy,Long>{
	
	Optional<LeavePolicy> findByMonthAndYear(Integer month, Integer year);
	Optional<LeavePolicy> findByYear(Integer year);
	Optional<LeavePolicy> findByYearAndType(Integer year, LeaveType type);
	

}
