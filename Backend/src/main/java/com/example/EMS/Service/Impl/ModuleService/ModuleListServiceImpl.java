package com.example.EMS.Service.Impl.ModuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.EMS.Service.ModuleService.ModuleListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EMS.EmployeeDTO.EmployeePermissionDTO;
import com.example.EMS.EmployeeDTO.ModuleListDTO.RolePermissionDTO;
import com.example.EMS.Entity.Module.ModuleEntity;
import com.example.EMS.Entity.Module.ModuleList;
import com.example.EMS.Entity.Module.UserModule;
import com.example.EMS.Entity.Role.Role;
import com.example.EMS.Repository.ModuleRepository.ModuleListRepository;
import com.example.EMS.Repository.ModuleRepository.ModuleRepository;
import com.example.EMS.Repository.ModuleRepository.UserModuleRepository;
import com.example.EMS.Repository.RoleRepository.RoleRepository;

@Service
@AllArgsConstructor
public class ModuleListServiceImpl implements ModuleListService {
	
	private final ModuleListRepository moduleListRepository;
	private final UserModuleRepository userModuleRepository;
	private final ModuleRepository moduleRepository;
	private final RoleRepository roleRepository;


	@Transactional
	public ResponseEntity<?> roleWisePermission(RolePermissionDTO dto, Long roleId, Long moduleId){ 
		
		List<ModuleList> list =  moduleListRepository.findByModuleIdAndRoleId(moduleId, roleId);
		
		Optional<UserModule> exist = userModuleRepository.findByUserModule_IdAndRole_Id(moduleId, roleId);
		UserModule userModule = null;
		if(exist.isEmpty()) {
			userModule = new UserModule();
		}
		else {
			userModule = exist.get();
		}
		
		Optional<ModuleEntity> entity = moduleRepository.findById(moduleId);
		if(entity.isEmpty()) {
			return ResponseEntity.badRequest().body("No module with id: "+moduleId);
		}
		
		
		Optional<Role> role = roleRepository.findById(roleId);
		if(role.isEmpty()) {
			return ResponseEntity.badRequest().body("No role with id: "+roleId);
		}
		
		userModule.setUserModule(entity.get());
		userModule.setRole(role.get());
		userModule.setCreatePermission(dto.isCreatePermission());
		userModule.setViewPermission(dto.isViewPermission());
		userModule.setEditPermission(dto.isEditPermission());
		userModule.setDeletePermission(dto.isDeletePermission());
		userModule.setApprovePermission(dto.isApprovePermission());
		userModule.setExportPermission(dto.isExportPermission());
		
		UserModule saved = userModuleRepository.save(userModule);
		if (role.get().getUserModule() == null) {
		    role.get().setUserModule(new ArrayList<>());
		}

		role.get().getUserModule().add(saved);

		roleRepository.save(role.get());
		

		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee list found with role id: "+roleId+" and module Id: "+moduleId);
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
