package com.example.EMS.Repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.Entity.Module.UserModule;

@Repository
public interface UserModuleRepository extends JpaRepository<UserModule, Long>{

	Optional<UserModule> findByUserModule_Id(Long moduleId);
	
	Optional<UserModule> findByUserModule_IdAndRole_Id(Long moduleId, Long roleId);
	
	List<UserModule> findAllByRole_Id(Long roleId);
	
}
