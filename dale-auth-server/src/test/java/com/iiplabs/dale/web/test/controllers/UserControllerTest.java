package com.iiplabs.dale.web.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.iiplabs.dale.web.controllers.UserController;
import com.iiplabs.dale.web.model.AuthorizationScope;
import com.iiplabs.dale.web.model.User;
import com.iiplabs.dale.web.reps.IScopeRepository;
import com.iiplabs.dale.web.reps.IUserRepository;
import com.iiplabs.dale.web.test.TestApplicationContextInitializer;
import com.iiplabs.dale.web.utils.StringUtil;

@ActiveProfiles("test")
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private UserController userController;

  @Autowired
	private IScopeRepository scopeRepository;
	
	@Autowired
	private IUserRepository userRepository;

  final private String testUserEmptyBodyJson = "{}";
  final private String testUserJson = "{\"email\": \"admin@online.com\"}";

  @BeforeAll
  public static void beforeAll() {
    //
  }

  @BeforeEach
  public void beforeEach() {
    //
  }

  @Test
  public void contextLoads() {
    //
  }

  @Test
  public void whenUserControllerInjected_thenNotNull() throws Exception {
    assertThat(userController).isNotNull();
  }
  
  @Test
  public void getUserScopesTest() throws Exception {
    // setup admin user
		String inetId = UUID.randomUUID().toString();
		String email = "admin@online.com";
		createAdminScopeAndUser(inetId, email);
    User u = userRepository.findByEmail(email).stream().findFirst().get();
    System.out.println("scopes=" + u.getScopes());

    String base64Email = StringUtil.toBase64(email);

    mockMvc.perform(get("/api/v1/scopes/" + base64Email))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", Matchers.hasSize(1)))
      .andExpect(jsonPath("$.[0]", Matchers.equalToIgnoringCase("admin")));
  }

  @Test
  public void getNonExistingUserScopesTest() throws Exception {
    String base64Email = "123";

    mockMvc.perform(get("/api/v1/scopes/" + base64Email))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", Matchers.hasSize(0)));
  }

  @Test
  public void createUserValidTokenWithAuthoritiesTest() throws Exception {
    mockMvc.perform(post("/api/v1/user")
        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin")))
      .contentType(MediaType.APPLICATION_JSON)
      .content(testUserJson))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void createUserValidTokenNoAuthoritiesTest_Forbidden() throws Exception {
    mockMvc.perform(post("/api/v1/user").with(jwt())
      .contentType(MediaType.APPLICATION_JSON)
      .content(testUserJson))
      .andDo(print())
      .andExpect(status().isForbidden());
  }

  @Test
  public void createUserNoTokenEmptyBodyTest() throws Exception {
    mockMvc.perform(post("/api/v1/user")
      .contentType(MediaType.APPLICATION_JSON)
      .content(testUserEmptyBodyJson))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void createUserNoTokenTest() throws Exception {
    mockMvc.perform(post("/api/v1/user")
      .contentType(MediaType.APPLICATION_JSON)
      .content(testUserJson))
      .andExpect(status().isUnauthorized());
  }

  private User createAdminScopeAndUser(String inetId, String email) {
    String adminScope = "admin";

		AuthorizationScope a = new AuthorizationScope();
		a.setInetId(UUID.randomUUID().toString());
		a.setName(adminScope);
		scopeRepository.save(a);
		
		User u = new User();
		u.setInetId(inetId);
		u.setEmail(email);
    u.setEnabled(true);
		u.addScope(scopeRepository.findByName(adminScope));
		
		return userRepository.save(u);
	}

}
