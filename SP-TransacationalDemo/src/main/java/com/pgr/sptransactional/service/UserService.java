package com.pgr.sptransactional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgr.sptransactional.entity.Address;
import com.pgr.sptransactional.entity.User;
import com.pgr.sptransactional.repository.AddressRepository;
import com.pgr.sptransactional.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;

	@Transactional
	public void registerUser(User user, Address address) {
		addressRepo.save(address);
		user.setAddress(address);
		userRepo.save(user);

		// Simulate failure to test rollback
		if (address.getCity().equals("Bangalore")) {
			throw new RuntimeException("Simulated failure");
		}
	}
}
