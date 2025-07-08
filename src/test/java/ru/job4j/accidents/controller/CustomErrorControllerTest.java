package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser
  void handleGenericError_ShouldReturnErrorView() throws Exception {
    mockMvc.perform(get("/error"))
        .andExpect(status().isOk())
        .andExpect(view().name("errors/error"))
        .andExpect(model().attributeDoesNotExist("status"));
  }

  @Test
  @WithMockUser
  void handleBadRequest_ShouldReturn400ErrorView() throws Exception {
    mockMvc.perform(get("/error/400"))
        .andExpect(status().isOk())
        .andExpect(view().name("errors/error-400"))
        .andExpect(model().attributeDoesNotExist("status"));
  }

  @Test
  @WithMockUser
  void handleForbidden_ShouldReturn403ErrorView() throws Exception {
    mockMvc.perform(get("/error/403"))
        .andExpect(status().isOk())
        .andExpect(view().name("errors/error-403"))
        .andExpect(model().attributeDoesNotExist("status"));
  }

  @Test
  @WithMockUser
  void handleNotFound_ShouldReturn404ErrorView() throws Exception {
    mockMvc.perform(get("/error/404"))
        .andExpect(status().isOk())
        .andExpect(view().name("errors/error-404"))
        .andExpect(model().attributeDoesNotExist("status"));
  }

  @Test
  @WithMockUser
  void handleServerError_ShouldReturn500ErrorView() throws Exception {
    mockMvc.perform(get("/error/500"))
        .andExpect(status().isOk())
        .andExpect(view().name("errors/error-500"))
        .andExpect(model().attributeDoesNotExist("status"));
  }

  @Test
  @WithMockUser
  void handleNonExistentErrorPage_ShouldReturn404() throws Exception {
    mockMvc.perform(get("/error/999"))
        .andExpect(status().isNotFound());
  }
}