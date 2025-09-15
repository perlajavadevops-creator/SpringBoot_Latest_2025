package com.pgr.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgr.restapi.dto.AuthenticationRequest;
import com.pgr.restapi.dto.AuthenticationResponse;
import com.pgr.restapi.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest request) {
        log.info("createToken(-)");
        // Authenticate the user
        userDetailsService.loadUserByUsername(request.getUsername());

        // Generate the token
        String jwtToken = jwtUtil.generateToken(request.getUsername());

        return new AuthenticationResponse(jwtToken);
    }
}