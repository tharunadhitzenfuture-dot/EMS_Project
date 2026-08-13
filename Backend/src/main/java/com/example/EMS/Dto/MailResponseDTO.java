package com.example.EMS.EmployeeDTO;

import lombok.Data;

@Data
public class MailResponseDTO {
	
	private String email;
	private String token;
	private String rawPassword;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRawPassword() {
		return rawPassword;
	}
	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}
	
	

}
