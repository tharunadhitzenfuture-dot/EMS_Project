package com.example.EMS.Service.ModuleService;

import com.example.EMS.EmployeeDTO.EmployeePermissionDTO;
import com.example.EMS.EmployeeDTO.ModuleListDTO.RolePermissionDTO;
import org.springframework.http.ResponseEntity;

public interface ModuleListService {

    ResponseEntity<?> roleWisePermission(RolePermissionDTO dto, Long roleId, Long moduleId);

    ResponseEntity<?> employeePermission(EmployeePermissionDTO dto, Long id);

}