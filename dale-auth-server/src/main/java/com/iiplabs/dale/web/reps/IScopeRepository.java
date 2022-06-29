package com.iiplabs.dale.web.reps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.iiplabs.dale.web.model.AuthorizationScope;

public interface IScopeRepository
		extends JpaRepository<AuthorizationScope, Long>, JpaSpecificationExecutor<AuthorizationScope> {

	@Transactional(readOnly = true)
	AuthorizationScope findByName(String name);

}
