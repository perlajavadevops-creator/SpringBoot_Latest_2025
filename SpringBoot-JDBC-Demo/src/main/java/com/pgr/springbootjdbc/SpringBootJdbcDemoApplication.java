package com.pgr.springbootjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pgr.springbootjdbc.beans.Employee;
import com.pgr.springbootjdbc.dao.EmployeeDao;

@SpringBootApplication
public class SpringBootJdbcDemoApplication implements CommandLineRunner {

	@Autowired
	private EmployeeDao employeeDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		employeeDao.saveEmployee(new Employee(103, "Dhatrika", 1000));
		/*
		 * boolean flag = employeeDao.saveEmployeeByPS(new Employee(104, "Mahesh",
		 * 90000)); System.out.println(flag);
		 */

		// List<Employee> list = employeeDao.getAllEmployees();
		// System.out.println(employeeDao.getEmployeeById(102));
		/*
		 * for(Employee emp : list) { System.out.println(emp); }
		 */
		// System.out.println(list);

		// employeeDao.udpateEmployeePS(new Employee(103, "Sai", 20000));

		employeeDao.deleteEmployeePS(104);
	}// save, retreiving,( delete, update)

}
