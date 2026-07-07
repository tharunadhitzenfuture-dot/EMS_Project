package com.example.EMS.EmployeeRepository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.User;
import com.example.EMS.EmployeeEntity.Module.ModuleEntity;
import com.example.EMS.EmployeeEntity.Module.ModuleList;


public interface ModuleListRepository extends JpaRepository<ModuleList, Long>{
	
	Optional<ModuleList> findByModuleAndUser(ModuleEntity module, User User);
	
	List<ModuleList> findByModuleIdAndRoleId(Long moduleId, Long roleId);
	
	Optional<ModuleList> findByUserIdAndModuleId(Long userId, Long moduleId); 

}
