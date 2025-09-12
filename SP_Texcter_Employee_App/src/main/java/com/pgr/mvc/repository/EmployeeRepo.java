package com.pgr.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.mvc.entity.Employee;



@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}