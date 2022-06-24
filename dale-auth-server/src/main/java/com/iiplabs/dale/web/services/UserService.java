package com.iiplabs.dale.web.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iiplabs.dale.web.model.User;
import com.iiplabs.dale.web.reps.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(readOnly=true)
	@Retryable
	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email).stream().findFirst();
	}
	
}
