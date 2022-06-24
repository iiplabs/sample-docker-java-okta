package com.iiplabs.dale.web.services;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ILocalJwtAuthenticationConverter extends Converter<Jwt, AbstractAuthenticationToken> {

}
