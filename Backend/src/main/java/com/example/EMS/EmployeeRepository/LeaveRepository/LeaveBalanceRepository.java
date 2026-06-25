package com.example.EMS.EmployeeRepository.LeaveRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.enums.Department;
import com.example.EMS.enums.LeaveType;

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
    
    Optional<LeaveBalance> findByEmployeeAndYearAndType(
    		Employee emp,
    		int year,
    		LeaveType type);
    
    Optional<LeaveBalance> findByEmployeeAndYearAndTypeAndDepartment(
    		Employee emp,
    		int year,
    		LeaveType type,
    		Department department);
    
    Optional<List<LeaveBalance>> findByDepartment(Department department);
    
}