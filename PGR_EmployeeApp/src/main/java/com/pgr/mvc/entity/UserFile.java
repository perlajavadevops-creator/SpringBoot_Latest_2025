package com.pgr.mvc.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "usersfile")
@AllArgsConstructor
@NoArgsConstructor
public class UserFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String fileName;

	@Lob
	@Column(nullable = false)
	private byte[] data; // actual file stored as blob

	public UserFile(String username, String fileName, byte[] data) {
		this.username = username;
		this.fileName = fileName;
		this.data = data;
	}

}
