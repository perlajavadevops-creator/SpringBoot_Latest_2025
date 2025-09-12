package com.pgr.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.mvc.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {

	@Query(value = "select * from login where username=?", nativeQuery = true )
	public LoginEntity getLoginDetailsByName(@Param("name") String name);
	
	@Query(value = "from LoginEntity where username=:nameParam")
	public LoginEntity getLoginDetailsByNameNonNativeQuery(@Param("nameParam") String nameParam);


}
