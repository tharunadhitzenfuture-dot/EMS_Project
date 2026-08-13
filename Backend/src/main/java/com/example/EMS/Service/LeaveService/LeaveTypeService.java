package com.example.EMS.Service.LeaveService;

import com.example.EMS.Entity.LeaveEntity.LeaveType;
import org.springframework.http.ResponseEntity;

public interface LeaveTypeService {

    ResponseEntity<?> createLeaveType(LeaveType type);

}