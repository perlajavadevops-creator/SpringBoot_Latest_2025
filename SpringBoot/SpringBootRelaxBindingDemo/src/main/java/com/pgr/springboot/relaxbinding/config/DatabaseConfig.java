package com.pgr.springboot.relaxbinding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "database")
@Setter
@Getter
public class DatabaseConfig {

	private String databaseName;
	private String userName;
	private String password;

	
}
