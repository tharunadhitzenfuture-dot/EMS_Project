package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmergencyContact {
    private String name;
    private String relation;
    private Long phone;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
    
    
}