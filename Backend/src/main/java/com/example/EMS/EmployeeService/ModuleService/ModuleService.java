package com.example.EMS.EmployeeService.ModuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.UserRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;
import com.example.EMS.EmployeeRepository.RoleRepository.RoleRepository;

@Service
public class ModuleService {
	
	private ModuleRepository repository;
	private UserRepository userRepository;
	private ModuleListRepository moduleListRepository;
	private RoleRepository roleRepository;
	


	public ModuleService(ModuleRepository repository, UserRepository userRepository,
			ModuleListRepository moduleListRepository, RoleRepository roleRepository) {

		this.repository = repository;
		this.userRepository = userRepository;
		this.moduleListRepository = moduleListRepository;
		this.roleRepository = roleRepository;
	}





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
