package com.pgr.h2db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pgr.h2db.bean.Employee;
import com.pgr.h2db.repository.EmployeeRepository;

@SpringBootApplication
public class SpJpaH2DbExampleApplication implements CommandLineRunner {

	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpJpaH2DbExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Employee> list = employeeRepository.findAll();

		list.forEach(empObj -> empObj.displayEmployee());

	}

}
