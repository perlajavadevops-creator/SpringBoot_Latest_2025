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
public class User {

	private Long id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private Integer age;

	private UserStatus status = UserStatus.ACTIVE;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public User(String username, String email, String firstName, String lastName, Integer age) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}


}