package com.pgr.sb.docker.service;

import java.util.List;
import java.util.Optional;

import com.pgr.sb.docker.entity.User;

public interface UserService {
	List<User> getAllUsers();

	Optional<User> getUserById(Long id);

	Optional<User> getUserByEmail(String email);

	User createUser(User user);

	User updateUser(Long id, User userDetails);

	void deleteUser(Long id);

	List<User> getUsersByFirstName(String firstName);

	List<User> getUsersByEmailDomain(String domain);

	boolean existsByEmail(String email);

	long getUserCount();
}