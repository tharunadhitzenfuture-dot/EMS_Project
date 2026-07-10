package com.example.EMS.EmployeeEntity.Module;



import com.example.EMS.EmployeeEntity.Role.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class UserModule {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	@JsonBackReference("user-module")
	private Role role;	

	@ManyToOne
	@JoinColumn(name="module_id")
	@JsonBackReference("user-moduleEntity")
    private ModuleEntity userModule;
	
	@JsonProperty("roleName")
	public String getRoleName() {
	    return role != null ? role.getRole() : null;
	}
	
	@JsonProperty("ModuleName")
	public String getModuleName() {
	    return userModule != null ? userModule.getModuleName() : null;
	}
	
	
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

	

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	

	public ModuleEntity getUserModule() {
		return userModule;
	}

	public void setUserModule(ModuleEntity userModule) {
		this.userModule = userModule;
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
