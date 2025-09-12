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

		if (login.getUsername().equals(loginEntity.getUsername())
				&& login.getPassword().equals(loginEntity.getPassword())) {
			return "Success";
		} else {
			return "Failed";
		}

	}

}
