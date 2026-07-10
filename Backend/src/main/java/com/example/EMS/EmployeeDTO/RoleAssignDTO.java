package com.example.EMS.EmployeeDTO;

import java.util.Set;

import lombok.Data;

@Data
public class RoleAssignDTO {
	
	private String role;
	private String parent_Role;
	private String description;
	private Set<String> sub_designation;
	
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
	public Set<String> getSub_designation() {
		return sub_designation;
	}
	public void setSub_designation(Set<String> sub_designation) {
		this.sub_designation = sub_designation;
	}
	
	

}
