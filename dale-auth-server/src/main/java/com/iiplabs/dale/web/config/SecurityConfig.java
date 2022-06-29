package com.iiplabs.dale.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.iiplabs.dale.web.services.ILocalJwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ILocalJwtAuthenticationConverter localJwtAuthenticationConverter;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		httpSecurity.addFilterBefore(filter, CsrfFilter.class);

		httpSecurity
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(localJwtAuthenticationConverter);
	}

}
