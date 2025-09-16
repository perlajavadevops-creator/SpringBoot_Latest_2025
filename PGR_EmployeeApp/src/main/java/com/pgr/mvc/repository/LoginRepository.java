package com.pgr.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.mvc.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
 // Find user by username using JPQL
	@Query(value="SELECT * FROM users  WHERE username = ?",nativeQuery=true)
	public LoginEntity getLoginDetailsByName(@Param("name") String name);
	
	@Query(value="FROM LoginEntity  WHERE username =:nameParam")
	public LoginEntity getLoginDetailsByNameNonNativeQuery(@Param("nameParam") String nameParam);
	
	LoginEntity findByUsername(String username);

}
