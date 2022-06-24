package com.iiplabs.dale.web.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.iiplabs.dale.web.controllers.UserController;
import com.iiplabs.dale.web.services.UserService;
import com.iiplabs.dale.web.test.TestApplicationContextInitializer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private UserController userController;

  @MockBean
  private UserService userService;

  @BeforeAll
  public static void setup() {
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
  
}
