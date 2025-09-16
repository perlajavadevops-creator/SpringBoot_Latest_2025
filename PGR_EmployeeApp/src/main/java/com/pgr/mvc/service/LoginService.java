package com.pgr.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginRepository;

	public String getLoginDetails(Login login) {
		LoginEntity loginEntity = loginRepository.getLoginDetailsByName(login.getUsername());

		
		if(loginEntity!=null && login.getUsername().equals(loginEntity.getUsername()) 
			&&login.getPassword().equals(loginEntity.getPassword()))
			{
			return "Success";
			
		}else {
		return "Failed";
		}

	}
	
	public String registerUser(Login login) {
        // Check if username already exists
        LoginEntity existingUser = loginRepository.findByUsername(login.getUsername());
        if (existingUser != null) {
            return "Username already taken. Please choose another.";
        }

        // Create new entity and save
        LoginEntity newUser = new LoginEntity();
        newUser.setId(0); // Let DB auto-generate if using auto-increment
        newUser.setUsername(login.getUsername());
        newUser.setEmail(login.getEmail());
        newUser.setPassword(login.getPassword());

        loginRepository.save(newUser);
        return "Registration successful!";
	}

	
}
