package com.pgr.paginationfeature.SP_PaginationFeature_Demo.bean.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.paginationfeature.SP_PaginationFeature_Demo.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
