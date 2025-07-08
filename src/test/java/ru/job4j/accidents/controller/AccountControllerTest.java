package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.config.SecurityConfig;
import ru.job4j.accidents.service.account.AccountService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  AccountService accountService;

  @Test
  @WithMockUser
  void getRegistrationPage() throws Exception {
    this.mockMvc.perform(get("/register"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("security/register"));
  }

  @Test
  @WithMockUser
  void loginPageThenGetLoginPage() throws Exception {
    this.mockMvc.perform(get("/login"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("/security/login"));
  }

  @Test
  @WithMockUser
  void loginPageWithLogoutThenGetLoginPage() throws Exception {
    this.mockMvc.perform(get("/login?logout=true"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("/security/login"))
        .andExpect(model().attributeExists("message"));
  }

  @Test
  @WithMockUser
  void loginPageWithErrorThenGetLoginPage() throws Exception {
    this.mockMvc.perform(get("/login?error=true"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("/security/login"))
        .andExpect(model().attributeExists("error"));
  }
}