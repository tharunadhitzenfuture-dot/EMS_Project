package com.example.EMS.EmployeeRepository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;


public interface ModuleListRepository extends JpaRepository<ModuleList, Long>{
	
	Optional<ModuleList> findByModuleAndEmployee(ModuleEntity module, Employee employee);
	
	List<ModuleList> findByModuleIdAndRoleId(Long moduleId, Long roleId);
	
	Optional<ModuleList> findByEmployeeIdAndModuleId(Long empId, Long moduleId); 

}
