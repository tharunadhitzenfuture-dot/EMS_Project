package com.example.EMS.EmployeeRepository.LeaveRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;



@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy,Long>{
	
	Optional<LeavePolicy> findByMonthAndYear(Integer month, Integer year);
	

}
