package com.example.EMS.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.Entity.ProfessionalDetails;

public interface ProfessionalDetailRepository 
        extends JpaRepository<ProfessionalDetails, Long> {

    ProfessionalDetails findByEmployeeEmployeeId(String employeeId);

}