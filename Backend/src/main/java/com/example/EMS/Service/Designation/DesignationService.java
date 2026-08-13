package com.example.EMS.Service.Designation;

import com.example.EMS.Entity.Designation;
import org.springframework.http.ResponseEntity;

public interface DesignationService {

    ResponseEntity<?> create(Designation designation);

    ResponseEntity<?> updateById(Long id, Designation request);

    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> deleteById(Long id);

}