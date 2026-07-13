package com.example.EMS.EmployeeConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeEntity.Module.UserModule;
import com.example.EMS.EmployeeRepository.UserRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.UserModuleRepository;

@Component
@Order(2)
public class ModuleDataLoader implements CommandLineRunner {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ModuleListRepository moduleListRepository;
    private final UserModuleRepository userModuleRepository;



	public ModuleDataLoader(ModuleRepository moduleRepository, UserRepository userRepository,
			ModuleListRepository moduleListRepository, UserModuleRepository userModuleRepository) {
		
		this.moduleRepository = moduleRepository;
		this.userRepository = userRepository;
		this.moduleListRepository = moduleListRepository;
		this.userModuleRepository = userModuleRepository;
	}


	@Override
    public void run(String... args) {

        String[] modules = {
                "Dashboard",
                "Employee",
                "Hr Attendance",
                "Leave Policy",
                "Emp Attendance",
                "HR Leave",
                "Emp Leave",
                "Payroll",
                "Project",
                "Client",
                "Requirement",
                "Documents",
                "Expenses",
                "Report",
                "Settings",
                "Company Settings",
                "Role Create",
                "Department",
                "Designation",
                "Menu Permission",
                "Shift Master",
                "Assign Shift"
        };

        for (String moduleName : modules) {

            if (!moduleRepository.existsByModuleName(moduleName)) {

                ModuleEntity module = new ModuleEntity();
                module.setModuleName(moduleName);

                ModuleEntity res = moduleRepository.save(module);

        		List<ModuleList> permissions = new ArrayList<>();
        		
        		Optional<User> exist = userRepository.findById(1L);
        		User user = exist.get();
        		
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
        		    
        		    
			    	permission.setCreatePermission(true);
					permission.setViewPermission(true);
					permission.setEditPermission(true);
					permission.setDeletePermission(true);
					permission.setApprovePermission(true);
					permission.setExportPermission(true);
        		    
        		    UserModule userModule = new  UserModule();
        		    userModule.setCreatePermission(true);
					userModule.setViewPermission(true);
					userModule.setEditPermission(true);
					userModule.setDeletePermission(true);
					userModule.setApprovePermission(true);
					userModule.setExportPermission(true);
					userModule.setRole(user.getRoleEntity());
					userModule.setUserModule(module);
					
					userModuleRepository.save(userModule);

        		    permissions.add(permission);
        		

        		moduleListRepository.saveAll(permissions);
        		res.setModuleList(permissions);
            }
        }

        System.out.println("Modules initialized successfully.");
    }
}