package com.example.EMS.Entity.Module;


import com.example.EMS.Entity.User;
import com.example.EMS.Entity.Role.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ModuleList {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("moduleName")
	public String getModuleName() {
	    return module != null ? module.getModuleName() : null;
	}
	
	@JsonProperty("roleName")
	public String getRoleName() {
	    return role != null ? role.getRole() : null;
	}
	
	@JsonProperty("empId")
	public String getEmpId() {

	    if (user == null || user.getEmployee() == null) {
	        return null;
	    }

	    return user.getEmployee().getEmployeeId();
	}
	
	@ManyToOne
	@JoinColumn(name="module_id")
	@JsonBackReference("module-moduleList")
	private ModuleEntity module;	
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonBackReference("user-moduleList")
	private User user;	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	@JsonBackReference("role-moduleList")
	private Role role;	
	
	private boolean createPermission;	
	private boolean viewPermission;	
	private boolean editPermission;	
	private boolean deletePermission;
	private boolean approvePermission;
	private boolean exportPermission;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ModuleEntity getModule() {
		return module;
	}
	public void setModule(ModuleEntity module) {
		this.module = module;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCreatePermission() {
		return createPermission;
	}
	public void setCreatePermission(boolean createPermission) {
		this.createPermission = createPermission;
	}
	public boolean isViewPermission() {
		return viewPermission;
	}
	public void setViewPermission(boolean viewPermission) {
		this.viewPermission = viewPermission;
	}
	public boolean isEditPermission() {
		return editPermission;
	}
	public void setEditPermission(boolean editPermission) {
		this.editPermission = editPermission;
	}
	public boolean isDeletePermission() {
		return deletePermission;
	}
	public void setDeletePermission(boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

	public boolean isApprovePermission() {
		return approvePermission;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setApprovePermission(boolean approvePermission) {
		this.approvePermission = approvePermission;
	}

	public boolean isExportPermission() {
		return exportPermission;
	}

	public void setExportPermission(boolean exportPermission) {
		this.exportPermission = exportPermission;
	}
	
    

}
