package com.example.EMS.EmployeeEntity;

import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class Experience {
	
	private String company_name;
	private String job_title;
	private String emp_type_prev;
	private Date emp_start;
	private Date emp_end;
	
	private String currently_working;
	private String Duration;
	private String tech_used;
	private String roles_responsibilities;
	private String exp_letter;
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getEmp_type_prev() {
		return emp_type_prev;
	}
	public void setEmp_type(String emp_type_prev) {
		this.emp_type_prev = emp_type_prev;
	}
	public Date getEmp_start() {
		return emp_start;
	}
	public void setEmp_start(Date emp_start) {
		this.emp_start = emp_start;
	}
	public Date getEmp_end() {
		return emp_end;
	}
	public void setEmp_end(Date emp_end) {
		this.emp_end = emp_end;
	}
	public String getCurrently_working() {
		return currently_working;
	}
	public void setCurrently_working(String currently_working) {
		this.currently_working = currently_working;
	}
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	public String getTech_used() {
		return tech_used;
	}
	public void setTech_used(String tech_used) {
		this.tech_used = tech_used;
	}
	public String getRoles_responsibilities() {
		return roles_responsibilities;
	}
	public void setRoles_responsibilities(String roles_responsibilities) {
		this.roles_responsibilities = roles_responsibilities;
	}
	public String getExp_letter() {
		return exp_letter;
	}
	public void setExp_letter(String exp_letter) {
		this.exp_letter = exp_letter;
	}
	
	
}
