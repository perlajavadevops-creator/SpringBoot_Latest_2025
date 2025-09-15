package com.pgr.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgr.restapi.dto.UserCreateRequest;
import com.pgr.restapi.dto.UserResponse;
import com.pgr.restapi.dto.UserStatus;
import com.pgr.restapi.entity.User;
import com.pgr.restapi.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users") // root uri// api/v1/users/8
@SecurityRequirement(name = "bearerAuth")
public class UserRestController {

	/*
	 * @GetMapping(value="/first", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<String> getHello() { User user = new User();
	 * user.setUsername("Reddy"); return new ResponseEntity(user, HttpStatus.OK); }
	 */

	@Autowired
	private UserService userService;

	// GET - Retrieve all users with pagination
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {

		Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<User> userPage = userService.findAll(pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("users", userPage.getContent());
		response.put("currentPage", userPage.getNumber());
		response.put("totalItems", userPage.getTotalElements());
		response.put("totalPages", userPage.getTotalPages());
		response.put("hasNext", userPage.hasNext());
		response.put("hasPrevious", userPage.hasPrevious());

		return ResponseEntity.ok(response);
	}

	// GET - Retrieve user by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		UserResponse user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	// GET - Search users by name
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
		List<User> users = userService.findByNameContaining(name);
		return ResponseEntity.ok(users);
	}

	// GET - Filter users by status
	@GetMapping("/status/{status}")
	public ResponseEntity<List<User>> getUsersByStatus(@PathVariable UserStatus status) {
		List<User> users = userService.findByStatus(status);
		return ResponseEntity.ok(users);
	}

	// POST - Create new user
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
		Map<String, Object> response = new HashMap<>();

		UserResponse savedUser = userService.createUser(userCreateRequest);
		response.put("message", "User created successfully");
		response.put("user", savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	// PUT - Update entire user
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateRequest userCreateRequest) {

		UserResponse updatedUser = userService.updateUser(id, userCreateRequest);

		return ResponseEntity.ok(updatedUser);
	}

	

	// DELETE - Delete user by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		userService.deleteUser(id);
		response.put("message", "User deleted successfully");

		return ResponseEntity.ok(response);
	}

}
