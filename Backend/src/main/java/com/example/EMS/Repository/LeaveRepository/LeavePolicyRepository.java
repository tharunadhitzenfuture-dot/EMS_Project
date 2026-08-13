package com.example.EMS.Repository.LeaveRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.EMS.Entity.LeaveEntity.LeavePolicy;
import com.example.EMS.Entity.LeaveEntity.LeaveType;





@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy,Long>{
	
	Optional<LeavePolicy> findByMonthAndYear(Integer month, Integer year);
	Optional<LeavePolicy> findByYear(Integer year);
	Optional<LeavePolicy> findByYearAndLeaveType(Integer year, LeaveType type);
	Optional<LeavePolicy> findByYearAndLeaveTypeAndDepartment_Name(Integer year, LeaveType type, String department);
	Optional<List<LeavePolicy>> findByDepartment_Name(String department);
	boolean existsByLeaveType(LeaveType leaveType);

}
