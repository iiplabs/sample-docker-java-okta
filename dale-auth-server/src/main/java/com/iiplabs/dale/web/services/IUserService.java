package com.iiplabs.dale.web.services;

import java.util.Optional;

import com.iiplabs.dale.web.model.User;

public interface IUserService {

	Optional<User> findByEmail(final String email);
	
}
