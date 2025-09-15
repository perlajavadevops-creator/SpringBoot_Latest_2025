package com.pgr.restapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	 private Long id;
	 private String username;
	 private String email;
	 private String firstName;
	 private String lastName;
	 private Integer age;
	 private UserStatus status;
	 private LocalDateTime createdAt;
	 private LocalDateTime updatedAt;

}
