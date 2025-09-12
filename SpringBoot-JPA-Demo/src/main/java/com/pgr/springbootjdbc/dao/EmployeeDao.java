package com.pgr.springbootjdbc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.springbootjdbc.beans.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	
	@Query(value = "select * from employee1 where name=?", nativeQuery = true )
	public Employee getByName(@Param("name") String name);
	
	@Query(value = "from Employee where name=:nameParam")
	public Employee getByNameUsingNonNativeQuery(@Param("nameParam") String nameParam);

	@Query(value = "from Employee")
	public List<Employee> findAllRecordsByJPQL();

}
