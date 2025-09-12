package com.pgr.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main_SetterDI {

	public static void main(String[] args) {
		/*Employee emp = new Employee(101, "PerlaReddy", 2000.9);
		emp.displayStudent();*/
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_SetterDI.xml");

		Employee emp1 = (Employee) context.getBean("employee");
		System.out.println(emp1.hashCode());
		emp1.displayStudent();

		System.out.println(emp1.getList());
		//emp2.displayStudent();
		//emp1.displayStudent();//1206883981 601893033
	}
}
