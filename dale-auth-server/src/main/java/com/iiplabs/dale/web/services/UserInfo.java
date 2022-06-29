package com.iiplabs.dale.web.services;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

	@JsonProperty("email")
	private String email;

	@JsonProperty("email_verified")
	private boolean emailVerified;

	@JsonProperty("name")
	private String name;

	@JsonProperty("nickname")
	private String nickName;

	@JsonProperty("picture")
	private String picture;

	@JsonProperty("sub")
	private String sub;

	@JsonProperty("updated_at")
	private Date updatedAt;

}
