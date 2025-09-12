package com.pgr.mvc.dto;

import jakarta.persistence.Column;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee6")
public class Employee {

	@Id
	@Column(name="id_val")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "id")
	private String empNo;
	@Column(name = "employeeName")
	private String employeeName;
	@Column(name = "employeeEmail")
	private String employeeEmail;
	@Column(name = "employeePhone")
	private Long employeePhone;
	@Column(name = "employeeGender")
	private String employeeGender;
	@Column(name = "employeeSalary")
	private String employeeSalary;
	@Column(name = "employeeRole")
	private String employeeRole;
}