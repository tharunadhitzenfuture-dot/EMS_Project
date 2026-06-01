package com.example.EMS.EmployeeRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.CompanyPolicy;

public interface CompanyPolicyRepository extends JpaRepository<CompanyPolicy, Long> {

}
