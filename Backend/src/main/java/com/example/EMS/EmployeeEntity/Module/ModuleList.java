package com.example.EMS.EmployeeEntity.Module;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.RolesAssign;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "module_permission",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "employee_id",
        "module_id"
    })
})
public class ModuleList {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="module_id")
	private ModuleEntity module;	
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;	
	@ManyToOne
	@JoinColumn(name="role_id")
	private RolesAssign rolesAssign;	
	private boolean createPermission;	
	private boolean viewPermission;	
	private boolean editPermission;	
	private boolean deletePermission;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public RolesAssign getRolesAssign() {
		return rolesAssign;
	}
	public void setRolesAssign(RolesAssign rolesAssign) {
		this.rolesAssign = rolesAssign;
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
    
	
	
	
	
	
	
	
	
	
	
	
}
