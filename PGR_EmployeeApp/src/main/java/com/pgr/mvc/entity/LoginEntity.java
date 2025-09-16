package com.pgr.mvc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Ensures DB auto-generates ID
	private int id;

	private String username;
	private String email;
	private String password;
}
