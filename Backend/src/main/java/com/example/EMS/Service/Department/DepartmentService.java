package com.example.EMS.Service.Department;

import com.example.EMS.Entity.Departments.Departments;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {

    ResponseEntity<?> create(List<Departments> request);

    ResponseEntity<?> update(Long id, Departments request);

    ResponseEntity<?> delete(Long id);

}