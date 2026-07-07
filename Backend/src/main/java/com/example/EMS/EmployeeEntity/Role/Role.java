package com.example.EMS.EmployeeEntity.Role;

import java.util.ArrayList;
import java.util.List;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(unique=true)
	private String role;
	private String parent_Role;
	private String description;
	
	@OneToMany(mappedBy = "roleEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("roleEntity-employee")
	private List<Employee> employee;
	
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("role-moduleList")
	private List<ModuleList> moduleList = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getParent_Role() {
		return parent_Role;
	}
	public void setParent_Role(String parent_Role) {
		this.parent_Role = parent_Role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ModuleList> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModuleList> moduleList) {
		this.moduleList = moduleList;
	}
	
	
	

}
