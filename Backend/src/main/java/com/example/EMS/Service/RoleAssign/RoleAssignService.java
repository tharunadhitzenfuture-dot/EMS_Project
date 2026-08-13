package com.example.EMS.Service.RoleAssign;

import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.RolesAssign;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface RoleAssignService {

    ResponseEntity<?> createRole(RolesAssign request);

    ResponseEntity<?> update(RolesAssign exist, RolesAssign request);

    List<Employee> searchEmployee(String department, String name);

}