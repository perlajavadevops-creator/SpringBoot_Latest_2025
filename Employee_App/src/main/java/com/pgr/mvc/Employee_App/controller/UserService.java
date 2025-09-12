package com.pgr.mvc.Employee_App.controller;

import com.pgr.mvc.Employee_App.dto.Login;

public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void saveUser(Login login);
}
