package com.iiplabs.dale.web.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserDto implements Serializable {

  @NotBlank(message="{validation.invalid_email}")
  @Email(message="{validation.invalid_email}")
  private String email;

}