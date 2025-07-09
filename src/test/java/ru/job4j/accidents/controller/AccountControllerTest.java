package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.config.SecurityConfig;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.account.AccountCreateDto;
import ru.job4j.accidents.model.Account;
import ru.job4j.accidents.service.account.AccountService;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  AccountService service;

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

  @Test
  @WithMockUser
  void whenRegisterThenRedirectToLoginPage() throws Exception {
    when(service.save(ArgumentMatchers.any(AccountCreateDto.class)))
        .thenReturn(Optional.of(new Account()));

    mockMvc.perform(post("/register")
            .param("name", "Name")
            .param("email", "Email")
            .param("password", "Password"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));

    ArgumentCaptor<AccountCreateDto> argument = ArgumentCaptor.forClass(AccountCreateDto.class);
    verify(service).save(argument.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
  }

  @Test
  @WithMockUser
  void whenRegisterAndErrorThenBackToRegisterPage() throws Exception {
    when(service.save(ArgumentMatchers.any(AccountCreateDto.class)))
        .thenReturn(Optional.empty());

    mockMvc.perform(post("/register")
            .param("name", "Name")
            .param("email", "Email")
            .param("password", "Password"))
        .andExpect(status().isOk())
        .andExpect(view().name("/security/register"))
        .andExpect(model().attributeExists("error"));

    ArgumentCaptor<AccountCreateDto> argument = ArgumentCaptor.forClass(AccountCreateDto.class);
    verify(service).save(argument.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
  }
}