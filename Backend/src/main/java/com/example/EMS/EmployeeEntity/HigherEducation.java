package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class HigherEducation {
	
	private String degree;
	private String instituition;
	private String specialization;
	private String degree_from;
	private String degree_to;
	private double percentage;
	private String certification;
	private String courseType;
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getInstituition() {
		return instituition;
	}
	public void setInstituition(String instituition) {
		this.instituition = instituition;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getDegree_from() {
		return degree_from;
	}
	public void setDegree_from(String degree_from) {
		this.degree_from = degree_from;
	}
	public String getDegree_to() {
		return degree_to;
	}
	public void setDegree_to(String degree_to) {
		this.degree_to = degree_to;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public String getCertification() {
		return certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	
	

}
