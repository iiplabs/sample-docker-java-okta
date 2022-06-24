package com.iiplabs.dale.web.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.iiplabs.dale.web.model.User;

public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Transactional(readOnly=true)
	Collection<User> findByEmail(String email);
	
}
