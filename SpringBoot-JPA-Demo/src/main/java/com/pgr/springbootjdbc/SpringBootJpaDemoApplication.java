package com.pgr.springbootjdbc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pgr.springbootjdbc.beans.Employee;
import com.pgr.springbootjdbc.dao.EmployeeDao;

@SpringBootApplication
public class SpringBootJpaDemoApplication implements CommandLineRunner  {

	@Autowired
	private EmployeeDao employeeDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * for (int i = 0; i < 200; i++) { Employee emp = new Employee(i,
		 * "Dhruvika Shree "+i, 3000+i); employeeDao.save(emp); }
		 * 
		 */
		/*
		 * Optional<Employee> emp1 = employeeDao.findById(104);
		 * emp1.get().displayEmployee();
		 */
		
		/*
		 * Employee emp2 = employeeDao.getByName("Gangi"); emp2.displayEmployee();
		 */
		
		/*
		 * Employee emp3 = employeeDao.getByNameUsingNonNativeQuery("Gangi");
		 * emp3.displayEmployee();
		 */
		
		
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

		//employeeDao.deleteEmployeePS(104);
		
		List<Employee> list = employeeDao.findAll();
		
		list.forEach(empObj -> empObj.displayEmployee());
	}// save, retreiving,( delete, update)


}
