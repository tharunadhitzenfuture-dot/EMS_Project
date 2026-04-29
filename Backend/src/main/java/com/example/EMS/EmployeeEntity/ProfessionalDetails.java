package com.example.EMS.EmployeeEntity;

import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProfessionalDetails {
	
	private String professional_designation;
	private String professional_department;
	private String emp_type;
	private String location;
	private String emp_status;
	private Date doj;
	private String probation_period;
	private Date confirmation_date;
	private String skills;
	private String exp_level;
	private String resume;
	private String offer_letter;
	
	
	
	public String getProfessional_department() {
		return professional_department;
	}
	public void setProfessional_department(String professional_department) {
		this.professional_department = professional_department;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getOffer_letter() {
		return offer_letter;
	}
	public void setOffer_letter(String offer_letter) {
		this.offer_letter = offer_letter;
	}
	
	public String getProfessional_designation() {
		return professional_designation;
	}
	public void setProfessional_designation(String professional_designation) {
		this.professional_designation = professional_designation;
	}
	public String getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getProbation_period() {
		return probation_period;
	}
	public void setProbation_period(String probation_period) {
		this.probation_period = probation_period;
	}
	public Date getConfirmation_date() {
		return confirmation_date;
	}
	public void setConfirmation_date(Date confirmation_date) {
		this.confirmation_date = confirmation_date;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getExp_level() {
		return exp_level;
	}
	public void setExp_level(String exp_level) {
		this.exp_level = exp_level;
	}
	
	
	
	
	

}
