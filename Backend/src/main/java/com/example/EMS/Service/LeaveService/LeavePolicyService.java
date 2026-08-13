package com.example.EMS.Service.LeaveService;

import com.example.EMS.Entity.LeaveEntity.LeavePolicy;
import org.springframework.http.ResponseEntity;

public interface LeavePolicyService {

    ResponseEntity<?> createPolicy(LeavePolicy request);

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> updateById(Long id, LeavePolicy request);

}