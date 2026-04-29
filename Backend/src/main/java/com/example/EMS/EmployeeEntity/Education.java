package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Education {
	
	private String degree;
	private String instituition;
	private String specialization;
	private String degree_from;
	private String degree_to;
	private double percentage;
	private String certification;
	private String courseType;
	
	private String educationLevel;
	private String educationalBoard;
	private String schoolName;
	private String school_from;
	private String school_to;
	private double school_percentage;
	private String education_pdf;
	
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
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getEducationalBoard() {
		return educationalBoard;
	}
	public void setEducationalBoard(String educationalBoard) {
		this.educationalBoard = educationalBoard;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchool_from() {
		return school_from;
	}
	public void setSchool_from(String school_from) {
		this.school_from = school_from;
	}
	public String getSchool_to() {
		return school_to;
	}
	public void setSchool_to(String school_to) {
		this.school_to = school_to;
	}
	public double getSchool_percentage() {
		return school_percentage;
	}
	public void setSchool_percentage(double school_percentage) {
		this.school_percentage = school_percentage;
	}
	public String getEducation_pdf() {
		return education_pdf;
	}
	public void setEducation_pdf(String education_pdf) {
		this.education_pdf = education_pdf;
	}
	
	
	
	

}
