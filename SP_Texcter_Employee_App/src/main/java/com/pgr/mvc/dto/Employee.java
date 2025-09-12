package com.pgr.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	private String id;
	private String employeeName;
	private String employeeEmail;
	private Long employeePhone;
	private String employeeGender;
	private String employeeSalary;
	private String employeeRole;
}