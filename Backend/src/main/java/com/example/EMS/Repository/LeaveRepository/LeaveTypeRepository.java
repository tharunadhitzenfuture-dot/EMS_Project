package com.example.EMS.Repository.LeaveRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.LeaveEntity.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>{
	
	Optional<LeaveType> findByName(String name);
}
