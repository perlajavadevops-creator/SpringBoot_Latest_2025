package com.pgr.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDto {

	private String username;
	private String newPassword;
	private String email;
	private String confirmPassword;
	

}
