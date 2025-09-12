package com.pgr.sb.docker.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgr.sb.docker.entity.User;
import com.pgr.sb.docker.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserById(Long id) {
		logger.info("Fetching user with id: {}", id);
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByEmail(String email) {
		logger.info("Fetching user with email: {}", email);
		return userRepository.findByEmail(email);
	}

	@Override
	public User createUser(User user) {
		logger.info("Creating new user with email: {}", user.getEmail());
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("User with email " + user.getEmail() + " already exists");
		}
		User savedUser = userRepository.save(user);
		logger.info("User created successfully with id: {}", savedUser.getId());
		return savedUser;
	}

	@Override
	public User updateUser(Long id, User userDetails) {
		logger.info("Updating user with id: {}", id);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		if (!user.getEmail().equals(userDetails.getEmail()) && userRepository.existsByEmail(userDetails.getEmail())) {
			throw new RuntimeException("User with email " + userDetails.getEmail() + " already exists");
		}

		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		user.setPhoneNumber(userDetails.getPhoneNumber());

		User updatedUser = userRepository.save(user);
		logger.info("User updated successfully with id: {}", updatedUser.getId());
		return updatedUser;
	}

	@Override
	public void deleteUser(Long id) {
		logger.info("Deleting user with id: {}", id);
		if (!userRepository.existsById(id)) {
			throw new RuntimeException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
		logger.info("User deleted successfully with id: {}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersByFirstName(String firstName) {
		logger.info("Fetching users with first name: {}", firstName);
		return userRepository.findByFirstNameIgnoreCase(firstName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersByEmailDomain(String domain) {
		logger.info("Fetching users with email domain: {}", domain);
		return userRepository.findByEmailDomain(domain);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public long getUserCount() {
		return userRepository.count();
	}
}