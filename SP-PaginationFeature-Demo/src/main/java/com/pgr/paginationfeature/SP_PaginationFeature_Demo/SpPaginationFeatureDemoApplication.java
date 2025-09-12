package com.pgr.paginationfeature.SP_PaginationFeature_Demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.pgr.paginationfeature.SP_PaginationFeature_Demo.bean.Employee;
import com.pgr.paginationfeature.SP_PaginationFeature_Demo.bean.repository.EmployeeRepository;

@SpringBootApplication
public class SpPaginationFeatureDemoApplication implements CommandLineRunner{

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpPaginationFeatureDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Page<Employee> listOfEmp =employeeRepository.findAll(PageRequest.of(1,100));
		System.out.println(listOfEmp.getSize());
		System.out.println(listOfEmp.getNumberOfElements());
		List<Employee> contentList =  listOfEmp.getContent();
		contentList.forEach(emp -> emp.displayEmployee());
	}

}
