package com.pgr.spring.beans;

import java.util.List;

public class Employee {

	private int empId;
	private String name;
	private double salary;
	private Address address;
	private List<String> list;
	public Employee() {
		System.out.println("Hi 0-arg con");
	}

	public Employee(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}

	public Employee(int empId, String name, double salary) {
		System.out.println("Hi 3-arg con");
		this.empId = empId;
		this.name = name;
		this.salary = salary;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void displayStudent() {
		System.out.println("Student Details are: ");
		System.out.println(empId);
		System.out.println(name);
		System.out.println(salary);
		System.out.println("Address class City: "+ address.getCity());
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}


class Address {
	private String city;

	public Address(){

	}
	public Address(String city) { this.city = city; }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}