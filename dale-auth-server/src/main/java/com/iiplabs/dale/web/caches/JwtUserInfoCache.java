package com.iiplabs.dale.web.caches;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iiplabs.dale.web.services.UserInfo;
import com.iiplabs.dale.web.utils.AuthenticationFacade;

import org.springframework.security.oauth2.jwt.Jwt;

public final class JwtUserInfoCache {

	public static final LoadingCache<Jwt, UserInfo> CACHE = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterAccess(5, TimeUnit.MINUTES)
			.softValues()
			.recordStats()
			.build(new CacheLoader<Jwt, UserInfo>() {

				@Override
				public UserInfo load(Jwt jwt) throws Exception {
					return AuthenticationFacade.getUserInfo(jwt);
				}
				
			});

	private JwtUserInfoCache() {
		throw new AssertionError();
	}
	
}
