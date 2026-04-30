package com.example.EMS.EmployeeEntity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

@Embeddable
public class Education {
	
	
	private String educationLevel;
	private String educationalBoard;
	private String schoolName;
	private String place;
	private String educationalGroup;
	private String school_from;
	private String school_to;
	
	private double school_percentage;
	private String education_pdf;
	@ElementCollection
	private List<HigherEducation> higherEducation;
	

	
	public List<HigherEducation> getHigherEducation() {
		return higherEducation;
	}
	public void setHigherEducation(List<HigherEducation> higherEducation) {
		this.higherEducation = higherEducation;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	

	public String getEducationalGroup() {
		return educationalGroup;
	}
	public void setEducationalGroup(String educationalGroup) {
		this.educationalGroup = educationalGroup;
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
