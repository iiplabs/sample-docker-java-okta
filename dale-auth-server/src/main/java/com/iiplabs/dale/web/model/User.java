package com.iiplabs.dale.web.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User extends BaseModel {

	@Column(name = "enabled")
	private boolean enabled;

	@NotNull
	@Size(max = 50)
	@Column(name = "email")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserScope> userScopes = new HashSet<>();

	public void addScope(AuthorizationScope a) {
		UserScope mm = new UserScope();
		mm.setInetId(UUID.randomUUID().toString());
		mm.setUser(this);
		mm.setScope(a);
		userScopes.add(mm);
	}

	public Collection<String> getScopes() {
		return userScopes.stream()
				.map(s -> s.getScope().getName())
				.sorted().collect(Collectors.toList());
	}

}
