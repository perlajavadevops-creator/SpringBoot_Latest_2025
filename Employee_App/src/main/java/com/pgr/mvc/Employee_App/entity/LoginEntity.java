package com.pgr.mvc.Employee_App.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {

	@Id
	private int id;
	private String username;
	private String password;
	private String email;

}
