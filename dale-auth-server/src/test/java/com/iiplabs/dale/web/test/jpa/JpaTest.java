package com.iiplabs.dale.web.test.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.iiplabs.dale.web.App;
import com.iiplabs.dale.web.model.AuthorizationScope;
import com.iiplabs.dale.web.model.User;
import com.iiplabs.dale.web.reps.IScopeRepository;
import com.iiplabs.dale.web.reps.IUserRepository;

@ContextConfiguration(classes = App.class)
@DataJpaTest
public class JpaTest {

	@Autowired
	private IScopeRepository scopeRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Test
	void injectedComponents() {
		assertThat(scopeRepository).isNotNull();
		assertThat(userRepository).isNotNull();
	}
	
	@Test
	void entityTest() {
		AuthorizationScope a = new AuthorizationScope();
		a.setInetId(UUID.randomUUID().toString());
		a.setName("admin");
		scopeRepository.save(a);
		
		User u = new User();
		u.setInetId(UUID.randomUUID().toString());
		u.setEmail("admin@online.com");
		u.addScope(scopeRepository.findByName("admin"));
		
		u = userRepository.save(u);
		
		assertThat(u.getScopes()).contains("admin");
	}
	
}