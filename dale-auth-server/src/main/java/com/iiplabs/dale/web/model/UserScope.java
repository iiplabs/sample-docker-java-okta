package com.iiplabs.dale.web.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "user_scopes")
public class UserScope extends BaseModel {

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "scope_user")
	@Fetch(FetchMode.JOIN)
	private User user;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "scope")
	@Fetch(FetchMode.JOIN)
	private AuthorizationScope scope;

}
