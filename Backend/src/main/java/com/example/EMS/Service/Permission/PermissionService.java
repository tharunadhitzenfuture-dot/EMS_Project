package com.example.EMS.Service.Permission;

import com.example.EMS.Entity.LeaveEntity.Permission;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PermissionService {

    ResponseEntity<?> applyPermission(String empId, Permission permission);

    ResponseEntity<?> reviewPermission(String empId, Long permissionId, Permission dto);

    ResponseEntity<?> updatePermission(String empId, Long permissionId, Permission request);

    ResponseEntity<?> deletePermission(String empId, Long permissionId);

    List<Permission> getAllPermission();

    Permission getPermissionById(Long id);

    List<Permission> getListPermissionById(Long empId);

}