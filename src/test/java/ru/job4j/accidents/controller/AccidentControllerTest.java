package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.rule.RuleService;
import ru.job4j.accidents.service.type.AccidentTypeService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccidentController.class)
class AccidentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AccidentService service;

  @MockitoBean
  private AccidentTypeService typeService;

  @MockitoBean
  private RuleService ruleService;

  @Test
  @WithMockUser
  void getAllAccidentsThenGetListPage() throws Exception {
    Mockito.when(service.findAll()).thenReturn(Collections.emptyList());

    this.mockMvc.perform(get("/api/accidents"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/list"))
        .andExpect(model().attributeExists("accidents"));
  }

  @Test
  @WithMockUser
  void getAccidentByIdThenGetViewPage() throws Exception {
    AccidentDto accident = new AccidentDto(1L, "Type", "Name", "Text", "Address", Set.of("Rule1"));
    Mockito.when(service.findById(1L)).thenReturn(Optional.of(accident));

    this.mockMvc.perform(get("/api/accidents/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/view"))
        .andExpect(model().attributeExists("accident"));
  }

  @Test
  @WithMockUser
  void getAccidentByIdWhenNotFoundThenRedirect() throws Exception {
    Mockito.when(service.findById(1L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/api/accidents/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
  }

  @Test
  @WithMockUser
  void getCreateFormThenGetCreatePage() throws Exception {
    Mockito.when(typeService.findAll()).thenReturn(Collections.emptyList());
    Mockito.when(ruleService.findAll()).thenReturn(Collections.emptyList());

    this.mockMvc.perform(get("/api/accidents/create"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/create"))
        .andExpect(model().attributeExists("types", "rules"));
  }

  @Test
  @WithMockUser
  void getEditFormThenGetEditPage() throws Exception {
    AccidentEditDto accident = new AccidentEditDto(1L, 1L, "Name", "Text", "Address", Set.of(1L));
    Mockito.when(service.findByIdOnEdit(1L)).thenReturn(Optional.of(accident));
    Mockito.when(typeService.findAll()).thenReturn(Collections.emptyList());
    Mockito.when(ruleService.findAll()).thenReturn(Collections.emptyList());

    this.mockMvc.perform(get("/api/accidents/1/edit"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/edit"))
        .andExpect(model().attributeExists("accident", "types", "rules"));
  }

  @Test
  @WithMockUser
  void getEditFormWhenNotFoundThenRedirect() throws Exception {
    Mockito.when(service.findByIdOnEdit(1L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/api/accidents/1/edit"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
  }
}