package com.pgr.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "First name is required")
	@Size(max = 50, message = "First name cannot exceed 50characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name cannot exceed 50characters")
	private String lastName;

	@NotNull(message = "Age is required")
	@Min(value = 18, message = "Age must be at least 18")
	@Max(value = 120, message = "Age must be less than 120")
	private Integer age;

}
