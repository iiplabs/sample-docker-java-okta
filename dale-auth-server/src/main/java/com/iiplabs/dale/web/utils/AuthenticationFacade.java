package com.iiplabs.dale.web.utils;

import java.time.Duration;
import java.util.Objects;

import com.iiplabs.dale.web.services.UserInfo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.WebClient;

public final class AuthenticationFacade {

	private AuthenticationFacade() {
		throw new AssertionError();
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static UserInfo getUserInfo() {
		return getUserInfo((Jwt) getAuthentication().getPrincipal());
	}

	/**
	 * Method locates /userinfo URI and uses it to fetch user info
	 * by the token, like email, etc.
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(Jwt principal) {
		return principal.getAudience().stream()
				.map(audience -> {
					UserInfo u = null;
					if (audience.endsWith("userinfo")) {
						u = retrieveUserInfo(audience, principal.getTokenValue());
					}
					return u;
				}).filter(Objects::nonNull).findFirst().orElseGet(UserInfo::new);
	}

	private static UserInfo retrieveUserInfo(String uri, String tokenValue) {
		WebClient client = WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenValue)
				.build();
		return client.get().uri(uri).retrieve().bodyToMono(UserInfo.class).block(Duration.ofSeconds(30));
	}

}
