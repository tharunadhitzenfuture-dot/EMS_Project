package com.example.EMS.EmployeeEntity;


import java.util.List;

import com.example.EMS.EmployeeEntity.Module.ModuleList;
import com.example.EMS.EmployeeEntity.Role.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String confirmPassword;
	private boolean active = true;
	
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "roleEntity_id")
	 @JsonBackReference("roleEntity-employee")
	 private Role roleEntity;
	
	@OneToOne(mappedBy="user")
	@JsonIgnore 
	private Employee employee;
	
	@OneToMany(mappedBy="user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("user-moduleList")
	private List<ModuleList> moduleList;
	
	
	

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Role getRoleEntity() {
		return roleEntity;
	}
	public void setRoleEntity(Role roleEntity) {
		this.roleEntity = roleEntity;
	}
	
	public List<ModuleList> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModuleList> moduleList) {
		this.moduleList = moduleList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



}
