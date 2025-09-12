package com.pgr.mvc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgr.mvc.dto.ForgotPasswordDto;
import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	LoginRepository loginRepository;

	public String getLoginDetails(Login login) {

		LoginEntity loginEntity = loginRepository.getLoginDetailsByName(login.getUsername());

		if (login.getUsername().equals(loginEntity.getUsername())
				&& login.getPassword().equals(loginEntity.getPassword())) {
			return "Success";
		} else {
			return "Failed";
		}

	}

	public boolean userRegistration(Login login) {
		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setEmail(login.getEmail());
		loginEntity.setUsername(login.getUsername());
		loginEntity.setPassword(login.getPassword());

		LoginEntity savedLoginentiEntity = loginRepository.save(loginEntity);
		return Optional.ofNullable(savedLoginentiEntity).isPresent();

	}

	public boolean userForgotPassword(ForgotPasswordDto forgotPasswordDto) {

		LoginEntity loginEntity = loginRepository.getLoginDetailsByName(forgotPasswordDto.getUsername());

		if (forgotPasswordDto.getNewPassword().equals(forgotPasswordDto.getConfirmPassword())) {
			loginEntity.setPassword(forgotPasswordDto.getNewPassword());
			
			loginRepository.save(loginEntity);
			return true;
		} else {
			return false;
		}
	}

}
