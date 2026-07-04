package com.example.EMS.EmployeeEntity.Module;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.RolesAssign;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	private Module module;	
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;	
	@ManyToOne
	@JoinColumn(name="role_id")
	private RolesAssign rolesAssign;	
	private boolean create;	
	private boolean view;	
	private boolean edit;	
	private boolean delete;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
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
	public boolean isCreate() {
		return create;
	}
	public void setCreate(boolean create) {
		this.create = create;
	}
	public boolean isView() {
		return view;
	}
	public void setView(boolean view) {
		this.view = view;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	
	
	
	
	
	
	
	
	
	
}
