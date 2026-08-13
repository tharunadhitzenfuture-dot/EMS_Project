package com.example.EMS.Service.Role;

import com.example.EMS.Entity.Role.Role;
import org.springframework.http.ResponseEntity;

public interface RoleService {

    ResponseEntity<?> createRole(Role request);

    ResponseEntity<?> update(Role exist, Role request);

}