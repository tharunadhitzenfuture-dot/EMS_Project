package com.example.EMS.EmployeeDTO;

import lombok.Data;

@Data
public class EmployeePermissionDTO {
	
	private String  empId;
	private Long moduleId;
	
	private boolean createPermission;	
	private boolean viewPermission;	
	private boolean editPermission;	
	private boolean deletePermission;
	private boolean approvePermission;
	private boolean exportPermission;
	
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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
