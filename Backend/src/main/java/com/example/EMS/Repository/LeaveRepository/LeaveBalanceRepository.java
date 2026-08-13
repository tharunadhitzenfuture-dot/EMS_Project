package com.example.EMS.Repository.LeaveRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.LeaveEntity.LeaveBalance;
import com.example.EMS.Entity.LeaveEntity.LeaveType;


import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    LeaveBalance findByEmployeeIdAndMonthAndYear(Long employeeId,Integer month, Integer year);
 //   Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, Integer year);
    
    Optional<LeaveBalance>
    findByEmployeeAndMonthAndYear(
            Employee employee,
            Integer month,
            Integer year
    );
    
    List<LeaveBalance>  findByEmployeeId(Long employeeId);
    
    Optional<LeaveBalance> findByEmployeeAndYearAndLeaveType(
    		Employee emp,
    		int year,
    		LeaveType type);
    
    Optional<LeaveBalance> findByEmployeeAndYearAndLeaveTypeAndDepartment_Name(
    		Employee emp,
    		int year,
    		LeaveType type,
    		String department);
    
    Optional<List<LeaveBalance>> findByDepartment_Name(String department);
    
}