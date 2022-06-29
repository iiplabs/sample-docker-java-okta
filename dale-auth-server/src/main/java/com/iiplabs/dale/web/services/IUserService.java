package com.iiplabs.dale.web.services;

import java.util.Optional;

import com.iiplabs.dale.web.model.User;
import com.iiplabs.dale.web.model.dto.UserDto;

public interface IUserService {

	Optional<User> findByEmail(final String email);
	
	User createUser(UserDto user);
	
}
