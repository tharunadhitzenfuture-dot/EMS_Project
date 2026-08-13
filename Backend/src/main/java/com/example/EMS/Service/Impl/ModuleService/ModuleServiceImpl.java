package com.example.EMS.Service.Impl.ModuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.EMS.Service.ModuleService.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EMS.Entity.User;
import com.example.EMS.Entity.Module.ModuleEntity;
import com.example.EMS.Entity.Module.ModuleList;
import com.example.EMS.Repository.UserRepository;
import com.example.EMS.Repository.ModuleRepository.ModuleListRepository;
import com.example.EMS.Repository.ModuleRepository.ModuleRepository;
import com.example.EMS.Repository.RoleRepository.RoleRepository;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {
	
	private final ModuleRepository repository;
	private final UserRepository userRepository;
	private final ModuleListRepository moduleListRepository;
	private final RoleRepository roleRepository;

	@Transactional
	public ResponseEntity<?> create(ModuleEntity request){	
		
		ModuleEntity res = repository.save(request);
		
		List<User> users = userRepository.findAll();

		List<ModuleList> permissions = new ArrayList<>();

		for (User user : users) {

			ModuleList permission = null;
		    Optional<ModuleList> moduleLst = moduleListRepository.findByModuleAndUser(res, user);
		    if(moduleLst.isEmpty()) {
		    	permission = new ModuleList();
		    }
		    else {
		    	permission = moduleLst.get();
		    }
		    

		    permission.setUser(user);
		    
		    permission.setRole(user.getRoleEntity());
		    permission.setModule(res);
		    
		    if(!user.getRoleEntity().getRole().equalsIgnoreCase("ADMIN")){
		    	 permission.setCreatePermission(false);
				 permission.setViewPermission(false);
				 permission.setEditPermission(false);
				 permission.setDeletePermission(false);
				 permission.setApprovePermission(false);
				 permission.setExportPermission(false);
		    }
		    else {
		    	 permission.setCreatePermission(true);
				 permission.setViewPermission(true);
				 permission.setEditPermission(true);
				 permission.setDeletePermission(true);
				 permission.setApprovePermission(true);
				 permission.setExportPermission(true);
		    }
		    
		   

		    permissions.add(permission);
		}

		moduleListRepository.saveAll(permissions);
		res.setModuleList(permissions);
		return ResponseEntity.ok(res);
		
	}
	

}
