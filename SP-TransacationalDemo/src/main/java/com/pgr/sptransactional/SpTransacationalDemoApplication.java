package com.pgr.sptransactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pgr.sptransactional.entity.Address;
import com.pgr.sptransactional.entity.User;
import com.pgr.sptransactional.service.UserService;

@SpringBootApplication
public class SpTransacationalDemoApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpTransacationalDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Address address = new Address();
		address.setCity("Bangalore");
		address.setState("KA1");
		User user = new User();
		user.setName("GangiReddy1");
		user.setAddress(address);

		userService.registerUser(user, address);

	}

}
