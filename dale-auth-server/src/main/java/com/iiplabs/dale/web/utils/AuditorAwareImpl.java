package com.iiplabs.dale.web.utils;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	private final static String SYSTEM_AUDITOR = "<system user>";

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String auditor = null != a ? a.getName() : SYSTEM_AUDITOR;
		return Optional.ofNullable(auditor);
	}

}
