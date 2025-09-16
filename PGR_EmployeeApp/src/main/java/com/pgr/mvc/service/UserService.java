package com.pgr.mvc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.repository.LoginRepository;
import com.pgr.mvc.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginRepository loginRepository;

	// Fetch user by ID
	public Login getUserById(int id) {
		Optional<LoginEntity> optionalUser = userRepository.findById(id);
		Login login = new Login();
		LoginEntity loginEntity = optionalUser.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
		login.setEmail(loginEntity.getEmail());
		login.setUsername(loginEntity.getUsername());
		return login;
	}

	// Update user details
	public LoginEntity updateUser(Login updatedUser, int id) {
		Optional<LoginEntity> optionalUser = userRepository.findById(id);
		LoginEntity existingUser = optionalUser
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
		existingUser.setUsername(updatedUser.getUsername());
		existingUser.setEmail(updatedUser.getEmail());
		// Add other fields as needed
		return userRepository.save(existingUser);
	}

	// 🔄 Create or Update user
	public LoginEntity saveOrUpdateUser(Login user) {
		LoginEntity entity = new LoginEntity();
		entity.setEmail(user.getEmail());
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());
		return userRepository.save(entity);
	}

	// 🔍 Read user by username (native query)
	public LoginEntity getUserByUsernameNative(String username) {
		return userRepository.getLoginDetailsByName(username);
	}

	// 🔍 Read user by username (JPQL)
	public LoginEntity getUserByUsernameJPQL(String username) {
		return loginRepository.getLoginDetailsByNameNonNativeQuery(username);
	}

	// 📋 Read all users
	public List<LoginEntity> getAllUsers() {
		return loginRepository.findAll();
	}

	public int updatePassword(String username, String newPassword) {
        return userRepository.updatePasswordByUsernameOrEmail(username, newPassword);
    }

	// 🗑️ Delete user by ID
	public void deleteUserById(int id) {
		userRepository.deleteById(id);
	}

	// 🗑️ Delete user by email
	public int deleteUserByEmail(String email) {
		return userRepository.deleteUserByEmail(email);
	}

}
