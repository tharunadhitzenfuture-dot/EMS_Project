package com.example.EMS.EmployeeEntity.Module;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Module {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moduleName;
    
	@OneToMany(mappedBy="module")
	private List<ModuleList> moduleList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<ModuleList> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ModuleList> moduleList) {
		this.moduleList = moduleList;
	}
    
    
}
