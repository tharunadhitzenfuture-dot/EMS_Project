package com.example.EMS.EmployeeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.EmployeeEntity.ShiftEmployeeDetails;

@Repository
public interface ShiftEmployeeDetailsRepository extends JpaRepository<ShiftEmployeeDetails, Long> {

}
