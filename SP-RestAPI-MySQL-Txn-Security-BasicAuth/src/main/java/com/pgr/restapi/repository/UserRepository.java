package com.pgr.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.restapi.dto.UserStatus;
import com.pgr.restapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	List<User> findByStatus(UserStatus status);

	List<User> findByAgeGreaterThanEqual(Integer age);

	@Query("SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name%")
	List<User> findByNameContaining(@Param("name") String name);//sridevi, sri, devi, srilaxmidevi -->sri

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
