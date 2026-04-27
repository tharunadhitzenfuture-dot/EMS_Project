package com.example.EMS.EmployeeEntity;

import java.util.Date;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String employee_code;
	private String first_name;
	private String last_name;
	private String email;
	private Long phone_number;
    private String gender;
    private String blood_group;
    private String aadhar_number;
    private String address;
    private Date date_of_birth;
    private Date date_of_joining;

    private String department;
    private String designation;
    private String marital_status;
    
    private String experience_type;
    private String experience_details;

    private String reporting_manager_id;
    private String status;

    private String employee_id;
    
    @Embedded
    private EmergencyContact emergency_contact;

    private Date createdAt;
    private Date updatedAt;
    
    
	private String imgFile;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmployee_code() {
		return employee_code;
	}


	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(Long phone_number) {
		this.phone_number = phone_number;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBlood_group() {
		return blood_group;
	}


	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}


	public String getAadhar_number() {
		return aadhar_number;
	}


	public void setAadhar_number(String aadhar_number) {
		this.aadhar_number = aadhar_number;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getDate_of_birth() {
		return date_of_birth;
	}


	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}


	public Date getDate_of_joining() {
		return date_of_joining;
	}


	public void setDate_of_joining(Date date_of_joining) {
		this.date_of_joining = date_of_joining;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getMarital_status() {
		return marital_status;
	}


	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}


	public String getExperience_type() {
		return experience_type;
	}


	public void setExperience_type(String experience_type) {
		this.experience_type = experience_type;
	}


	public String getExperience_details() {
		return experience_details;
	}


	public void setExperience_details(String experience_details) {
		this.experience_details = experience_details;
	}


	public String getReporting_manager_id() {
		return reporting_manager_id;
	}


	public void setReporting_manager_id(String reporting_manager_id) {
		this.reporting_manager_id = reporting_manager_id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getEmployee_id() {
		return employee_id;
	}


	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}


	public EmergencyContact getEmergency_contact() {
		return emergency_contact;
	}


	public void setEmergency_contact(EmergencyContact emergency_contact) {
		this.emergency_contact = emergency_contact;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getImgFile() {
		return imgFile;
	}


	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	
	
	

	
	
	

}
