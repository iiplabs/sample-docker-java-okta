package com.iiplabs.dale.web.test.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		String inetId = UUID.randomUUID().toString();
		String email = "admin@online.com";
		User user = createAdminScopeAndUser(inetId, email);

		assertEquals(inetId, user.getInetId());
		assertThat(user.getScopes()).contains("admin");
	}

	private User createAdminScopeAndUser(String inetId, String email) {
		String adminScope = "admin";

		AuthorizationScope a = new AuthorizationScope();
		a.setInetId(UUID.randomUUID().toString());
		a.setName(adminScope);
		scopeRepository.save(a);

		User u = new User();
		u.setInetId(inetId);
		u.setEmail(email);
		u.setEnabled(true);
		u.addScope(scopeRepository.findByName(adminScope));

		return userRepository.save(u);
	}

}