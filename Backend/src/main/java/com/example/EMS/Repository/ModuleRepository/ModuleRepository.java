package com.example.EMS.Repository.ModuleRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.Entity.Module.ModuleEntity;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long>{

	Optional<ModuleEntity> findByModuleName(String name);
    boolean existsByModuleName(String moduleName);
}
