package com.pgr.h2db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.h2db.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
