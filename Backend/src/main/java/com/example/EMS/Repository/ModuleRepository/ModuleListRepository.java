package com.example.EMS.Repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.EMS.Entity.User;
import com.example.EMS.Entity.Module.ModuleEntity;
import com.example.EMS.Entity.Module.ModuleList;


public interface ModuleListRepository extends JpaRepository<ModuleList, Long>{
	
	Optional<ModuleList> findByModuleAndUser(ModuleEntity module, User User);
	
	List<ModuleList> findByModuleIdAndRoleId(Long moduleId, Long roleId);
	
	Optional<ModuleList> findByUserIdAndModuleId(Long userId, Long moduleId); 

	List<ModuleList> findByRoleId(Long roleId);
	
	List<ModuleList> findByUserId(Long userId);
}
