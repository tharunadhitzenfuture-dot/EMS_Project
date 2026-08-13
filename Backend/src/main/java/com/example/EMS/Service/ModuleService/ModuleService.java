package com.example.EMS.Service.ModuleService;

import com.example.EMS.Entity.Module.ModuleEntity;
import org.springframework.http.ResponseEntity;

public interface ModuleService {

    ResponseEntity<?> create(ModuleEntity request);

}