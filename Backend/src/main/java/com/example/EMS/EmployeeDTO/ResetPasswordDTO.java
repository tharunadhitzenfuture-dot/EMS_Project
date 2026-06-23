package com.example.EMS.EmployeeDTO;

import lombok.Data;

@Data
public class ResetPasswordDTO {
	
	
	private String oldPassword;
	private String password;
	private String confirmPassword;

}
