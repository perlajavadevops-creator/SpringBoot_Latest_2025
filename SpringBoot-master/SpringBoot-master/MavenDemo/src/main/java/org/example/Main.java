package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
		/*Employee emp = new Employee(101, "PerlaReddy", 2000.9);
		emp.displayStudent();*/

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Employee emp1 = (Employee) context.getBean("employee1");
        //Employee emp2 = (Employee) context.getBean("employee2");
        Employee emp1 = (Employee) context.getBean("employee");
        System.out.println(emp1.hashCode());
        emp1.displayStudent();
        //emp2.displayStudent();
        //emp1.displayStudent();//1206883981 601893033
    }
}