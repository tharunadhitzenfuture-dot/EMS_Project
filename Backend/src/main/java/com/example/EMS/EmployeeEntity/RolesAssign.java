package com.example.EMS.EmployeeEntity;

import java.util.List;
import java.util.Set;

import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class RolesAssign {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String role;
	private String parent_Role;
	private String description;
	
	private Set<String> sub_designation;
	
	@OneToMany(mappedBy = "rolesAssign")
	@JsonManagedReference("role-employee")
	private List<Employee> employee;
	
	@OneToMany(mappedBy="rolesAssign")
	@JsonManagedReference("role-moduleList")
	private List<ModuleList> moduleList;

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

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getSub_designation() {
		return sub_designation;
	}

	public void setSub_designation(Set<String> sub_designation) {
		this.sub_designation = sub_designation;
	}

	public List<ModuleList> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ModuleList> moduleList) {
		this.moduleList = moduleList;
	}
    
	
	
	
	
	

}
