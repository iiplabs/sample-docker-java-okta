package com.iiplabs.dale.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iiplabs.dale.web.annotations.RestControllerAnnotation;
import com.iiplabs.dale.web.model.User;
import com.iiplabs.dale.web.model.dto.UserDto;
import com.iiplabs.dale.web.services.IUserService;
import com.iiplabs.dale.web.utils.StringUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAnnotation
@RequestMapping("/api/v1")
@RestController
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @PreAuthorize("permitAll()")
    @GetMapping("/scopes/{base64Email}")
    public Collection<String> getUserScopes(@PathVariable String base64Email) {
        String email = StringUtil.fromBase64(base64Email);
        log.info("Looking up user scopes for email {}", email);

        Collection<String> c = new ArrayList<>();
        Optional<User> oUser = userService.findByEmail(email);
        if (oUser.isPresent()) {
            c.addAll(oUser.get().getScopes());
        }
        return c;
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

}
