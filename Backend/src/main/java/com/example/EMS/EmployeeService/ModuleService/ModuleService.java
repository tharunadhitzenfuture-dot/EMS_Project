package com.example.EMS.EmployeeService.ModuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeRepository.EmpRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleListRepository;
import com.example.EMS.EmployeeRepository.ModuleRepository.ModuleRepository;

@Service
public class ModuleService {
	
	private ModuleRepository repository;
	private EmpRepository employeeRepository;
	private ModuleListRepository moduleListRepository;

	
	
	
	public ModuleService(ModuleRepository repository, EmpRepository employeeRepository,
			ModuleListRepository moduleListRepository) {
	
		this.repository = repository;
		this.employeeRepository = employeeRepository;
		this.moduleListRepository = moduleListRepository;
	}




	public ResponseEntity<?> create(ModuleEntity request){
	
		ModuleEntity res = repository.save(request);
		
		List<Employee> employees = employeeRepository.findAll();

		List<ModuleList> permissions = new ArrayList<>();

		for (Employee employee : employees) {

			ModuleList permission = null;
		    Optional<ModuleList> moduleLst = moduleListRepository.findByModuleAndEmployee(res, employee);
		    if(moduleLst.isEmpty()) {
		    	permission = new ModuleList();
		    }
		    else {
		    	permission = moduleLst.get();
		    }
		    

		    permission.setEmployee(employee);
		    permission.setRolesAssign(employee.getRolesAssign());
		    permission.setModule(res);

		    permission.setCreatePermission(false);
		    permission.setViewPermission(false);
		    permission.setEditPermission(false);
		    permission.setDeletePermission(false);

		    permissions.add(permission);
		}

		moduleListRepository.saveAll(permissions);
		res.setModuleList(permissions);
		return ResponseEntity.ok(res);
		
	}
	

}
