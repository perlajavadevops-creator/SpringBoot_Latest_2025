package com.pgr.restapi.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pgr.restapi.dto.UserStatus;
import com.pgr.restapi.entity.User;

@Repository
public interface UserRepositoryNew extends JpaRepository<User, Long> {

	// 🔍 Method-name queries
	List<User> findByFirstNameAndLastName(String firstName, String lastName);

	List<User> findByAgeGreaterThanEqualOrderByAgeAsc(Integer age);

	Page<User> findByStatusAndAgeGreaterThan(UserStatus status, Integer age, Pageable pageable);

	// 📅 JPQL query for date range
	@Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
	List<User> findUsersCreatedBetween(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	// 🔎 JPQL query with dynamic filters
	@Query("SELECT u FROM User u WHERE " + "(:username IS NULL OR u.username LIKE CONCAT('%', :username, '%')) AND "
			+ "(:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) AND "
			+ "(:status IS NULL OR u.status = :status)")
	Page<User> findUsersWithFilters(@Param("username") String username, @Param("email") String email,
			@Param("status") UserStatus status, Pageable pageable);

	// 🛠️ Native SQL query
	@Query(value = "SELECT * FROM users u WHERE u.age > :minAge AND u.status = :status", nativeQuery = true)
	List<User> findActiveUsersAboveAge(@Param("minAge") Integer minAge, @Param("status") String status);

	// 🔧 Update query
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.status = :status WHERE u.id IN :ids")
	int updateUserStatus(@Param("ids") List<Long> ids, @Param("status") UserStatus status);

	// 📊 Aggregation queries
	@Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
	long countByStatus(@Param("status") UserStatus status);

	@Query("SELECT AVG(u.age) FROM User u WHERE u.status = 'ACTIVE'")
	Double findAverageAgeOfActiveUsers();
	
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	List<User> findByStatus(UserStatus status);

	List<User> findByAgeGreaterThanEqual(Integer age);

	@Query("SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name%")
	List<User> findByNameContaining(@Param("name") String name);//sridevi, sri, devi, srilaxmidevi -->sri

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}