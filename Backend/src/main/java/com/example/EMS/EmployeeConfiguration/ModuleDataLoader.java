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
import com.example.EMS.EmployeeRepository.UserRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;

@Component
@Order(2)
public class ModuleDataLoader implements CommandLineRunner {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ModuleListRepository moduleListRepository;


	public ModuleDataLoader(ModuleRepository moduleRepository, UserRepository userRepository,
			ModuleListRepository moduleListRepository) {

		this.moduleRepository = moduleRepository;
		this.userRepository = userRepository;
		this.moduleListRepository = moduleListRepository;
	}







	@Override
    public void run(String... args) {

        String[] modules = {
                "Dashboard",
                "Employee",
                "Hr Attendance",
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
            }
        }

        System.out.println("Modules initialized successfully.");
    }
}