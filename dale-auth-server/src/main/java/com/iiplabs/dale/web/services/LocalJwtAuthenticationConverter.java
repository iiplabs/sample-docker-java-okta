package com.iiplabs.dale.web.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.iiplabs.dale.web.caches.JwtUserInfoCache;
import com.iiplabs.dale.web.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class LocalJwtAuthenticationConverter implements ILocalJwtAuthenticationConverter {

	private static final String DEFAULT_AUTHORITY_PREFIX = "SCOPE_";

	private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("scope", "scp");

	@Autowired
	private IUserService userService;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		// standard code
		authorities.addAll(getAuthorities(jwt).stream()
				.map(s -> new SimpleGrantedAuthority(DEFAULT_AUTHORITY_PREFIX + s))
				.collect(Collectors.toList()));

		// additional role population
		try {
			UserInfo userInfo = JwtUserInfoCache.CACHE.get(jwt);
			authorities.addAll(userService.findByEmail(userInfo.getEmail()).orElseGet(User::new).getScopes().stream()
					.map(s -> new SimpleGrantedAuthority(DEFAULT_AUTHORITY_PREFIX + s)).collect(Collectors.toList()));
		} catch (ExecutionException e) {
			log.error(e, e);
		}

		return new JwtAuthenticationToken(jwt, authorities);
	}

	private String getAuthoritiesClaimName(Jwt jwt) {
		for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
			if (Boolean.TRUE.equals(jwt.containsClaim(claimName))) {
				return claimName;
			}
		}
		return null;
	}

	private Collection<String> getAuthorities(Jwt jwt) {
		String claimName = getAuthoritiesClaimName(jwt);

		if (claimName == null) {
			return Collections.emptyList();
		}

		Object authorities = jwt.getClaim(claimName);
		if (authorities instanceof String) {
			if (StringUtils.hasText((String) authorities)) {
				return Arrays.asList(((String) authorities).split(" "));
			} else {
				return Collections.emptyList();
			}
		} else if (authorities instanceof Collection) {
			return (Collection<String>) authorities;
		}

		return Collections.emptyList();
	}

}
