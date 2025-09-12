package com.pgr.sb.docker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.sb.docker.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Find user by email
	Optional<User> findByEmail(String email);

	// Find users by first name (case insensitive)
	List<User> findByFirstNameIgnoreCase(String firstName);

	// Find users by last name (case insensitive)
	List<User> findByLastNameIgnoreCase(String lastName);

	// Find users by first name and last name
	List<User> findByFirstNameAndLastName(String firstName, String lastName);

	// Custom query to find users by email domain
	@Query("SELECT u FROM User u WHERE u.email LIKE %:domain%")
	List<User> findByEmailDomain(@Param("domain") String domain);

	// Check if email exists
	boolean existsByEmail(String email);

	// Count users by first name
	long countByFirstName(String firstName);
}
