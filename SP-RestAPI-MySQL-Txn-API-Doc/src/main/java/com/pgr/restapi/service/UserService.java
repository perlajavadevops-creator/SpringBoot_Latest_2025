package com.pgr.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgr.restapi.dto.UserCreateRequest;
import com.pgr.restapi.dto.UserResponse;
import com.pgr.restapi.dto.UserStatus;
import com.pgr.restapi.entity.User;
import com.pgr.restapi.exception.DuplicateUserException;
import com.pgr.restapi.exception.UserNotFoundException;
import com.pgr.restapi.repository.UserRepositoryNew;

@Service
public class UserService {

	@Autowired
    private  UserRepositoryNew userRepositoryNew;

    // 🔍 Get user by ID
    public UserResponse getUserById(Long id) {
        User user = userRepositoryNew.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        return convertToResponse(user);
    }

    // 🆕 Create new user
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepositoryNew.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already exists: " + request.getUsername());
        }
        if (userRepositoryNew.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already exists: " + request.getEmail());
        }

        User user = new User(
            request.getUsername(),
            request.getEmail(),
            request.getFirstName(),
            request.getLastName(),
            request.getAge(),
            request.getPassword(),
            request.isActive()
        );

        User savedUser = userRepositoryNew.save(user);
        return convertToResponse(savedUser);
    }

    // ✏️ Update existing user
    public UserResponse updateUser(Long id, UserCreateRequest request) {
        User existingUser = userRepositoryNew.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        if (!existingUser.getUsername().equals(request.getUsername()) &&
            userRepositoryNew.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already exists: " + request.getUsername());
        }

        if (!existingUser.getEmail().equals(request.getEmail()) &&
            userRepositoryNew.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already exists: " + request.getEmail());
        }

        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setAge(request.getAge());
        existingUser.setPassword(request.getPassword());
        existingUser.setActive(request.isActive());
        User updatedUser = userRepositoryNew.save(existingUser);
        return convertToResponse(updatedUser);
    }

    // ❌ Delete user
    public void deleteUser(Long id) {
        if (!userRepositoryNew.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepositoryNew.deleteById(id);
    }

    // 🔎 Search users by name
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsersByName(String name) {
        return userRepositoryNew.findByNameContaining(name)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    // 🔧 Bulk update user status
    public List<UserResponse> updateUsersStatus(List<Long> userIds, UserStatus status) {
        userRepositoryNew.updateUserStatus(userIds, status);
        return userRepositoryNew.findAllById(userIds)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    // 🧾 Convert entity to response DTO
    private UserResponse convertToResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getAge(),
            user.getStatus(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getPassword(),
            user.isActive()
        );
    }

	public Page<User> findAll(Pageable pageable) {
		return userRepositoryNew.findAll(pageable);
	}

	public List<User> findByNameContaining(String name) {
		return userRepositoryNew.findByNameContaining(name);
	}

	public List<User> findByStatus(UserStatus status) {
		return userRepositoryNew.findByStatus(status);
	}
}