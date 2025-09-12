package com.pgr.sb.docker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgr.sb.docker.entity.User;
import com.pgr.sb.docker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("GET /users - Fetching all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("GET /users/{} - Fetching user by id", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        logger.info("GET /users/email/{} - Fetching user by email", email);
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        logger.info("POST /users - Creating new user");
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            logger.error("Error creating user: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        logger.info("PUT /users/{} - Updating user", id);
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Error updating user: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /users/{} - Deleting user", id);
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/search/firstname/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        logger.info("GET /users/search/firstname/{} - Searching users by first name", firstName);
        List<User> users = userService.getUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/domain/{domain}")
    public ResponseEntity<List<User>> getUsersByEmailDomain(@PathVariable String domain) {
        logger.info("GET /users/search/domain/{} - Searching users by email domain", domain);
        List<User> users = userService.getUsersByEmailDomain(domain);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getUserCount() {
        logger.info("GET /users/count - Getting user count");
        long count = userService.getUserCount();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "User Service");
        return ResponseEntity.ok(response);
    }
}