package com.example.EMS.EmployeeService.ModuleService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeDTO.EmployeePermissionDTO;
import com.example.EMS.EmployeeDTO.ModuleListDTO.RolePermissionDTO;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;

@Service
public class ModuleListService {
	
	private ModuleListRepository moduleListRepository;
	

	public ModuleListService(ModuleListRepository moduleListRepository) {
		this.moduleListRepository = moduleListRepository;
	}

	public ResponseEntity<?> roleWisePermission(RolePermissionDTO dto, Long roleId, Long moduleId){
		
		List<ModuleList> list =  moduleListRepository.findByModuleIdAndRoleId(moduleId, roleId);
		
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records found with role id: "+roleId+" and module Id: "+moduleId);
		}
		
		for(ModuleList module: list) {
			   module.setCreatePermission(dto.isCreatePermission());
			   module.setViewPermission(dto.isViewPermission());
			   module.setEditPermission(dto.isEditPermission());
			   module.setDeletePermission(dto.isDeletePermission());
			   module.setApprovePermission(dto.isApprovePermission());
			   module.setExportPermission(dto.isExportPermission());
		}
		
		moduleListRepository.saveAll(list);
		
		return ResponseEntity.ok("Permissions updated successfully");
	}
	
	
	public ResponseEntity<?> employeePermission(EmployeePermissionDTO dto, Long id){
		
		 Optional<ModuleList> modules = moduleListRepository.findByUserIdAndModuleId(id, dto.getModuleId());
		 
		 if(modules.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records found with employee id: "+id+" and module Id: "+dto.getModuleId());
		 }
		 ModuleList module = modules.get();
		 module.setCreatePermission(dto.isCreatePermission());
		 module.setViewPermission(dto.isViewPermission());
		 module.setEditPermission(dto.isEditPermission());
		 module.setDeletePermission(dto.isDeletePermission());
		 module.setApprovePermission(dto.isApprovePermission());
		 module.setExportPermission(dto.isExportPermission());
		 
		 ModuleList res  = moduleListRepository.save(module);
		 
		 return ResponseEntity.ok(res);
	}

}
