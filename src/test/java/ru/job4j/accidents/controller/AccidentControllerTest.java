package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.config.SecurityConfig;
import ru.job4j.accidents.dto.accident.AccidentCreateDto;
import ru.job4j.accidents.dto.accident.AccidentDto;
import ru.job4j.accidents.dto.accident.AccidentEditDto;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.rule.RuleService;
import ru.job4j.accidents.service.type.AccidentTypeService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccidentController.class)
@Import(SecurityConfig.class)
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
    when(service.findAll()).thenReturn(Collections.emptyList());

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
    when(service.findById(1L)).thenReturn(Optional.of(accident));

    this.mockMvc.perform(get("/api/accidents/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/view"))
        .andExpect(model().attributeExists("accident"));
  }

  @Test
  @WithMockUser
  void getAccidentByIdWhenNotFoundThenRedirect() throws Exception {
    when(service.findById(1L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/api/accidents/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
  }

  @Test
  @WithMockUser
  void getCreateFormThenGetCreatePage() throws Exception {
    when(typeService.findAll()).thenReturn(Collections.emptyList());
    when(ruleService.findAll()).thenReturn(Collections.emptyList());

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
    when(service.findByIdOnEdit(1L)).thenReturn(Optional.of(accident));
    when(typeService.findAll()).thenReturn(Collections.emptyList());
    when(ruleService.findAll()).thenReturn(Collections.emptyList());

    this.mockMvc.perform(get("/api/accidents/1/edit"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("accidents/edit"))
        .andExpect(model().attributeExists("accident", "types", "rules"));
  }

  @Test
  @WithMockUser
  void getEditFormWhenNotFoundThenRedirect() throws Exception {
    when(service.findByIdOnEdit(1L)).thenReturn(Optional.empty());

    this.mockMvc.perform(get("/api/accidents/1/edit"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
  }

  @Test
  @WithMockUser
  void updateAccidentWhenSuccessShouldRedirectToViewPage() throws Exception {
    when(service.update(ArgumentMatchers.any(AccidentEditDto.class), ArgumentMatchers.anySet()))
        .thenReturn(true);

    mockMvc.perform(put("/api/accidents/1/edit")
            .param("id", "1")
            .param("name", "Name")
            .param("text", "Text")
            .param("address", "Address")
            .param("typeId", "1")
            .param("ruleIds", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/api/accidents/1"));
    ArgumentCaptor<AccidentEditDto> argument = ArgumentCaptor.forClass(AccidentEditDto.class);
    ArgumentCaptor<Set<Long>> argument2 = ArgumentCaptor.forClass(Set.class);
    verify(service).update(argument.capture(), argument2.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
    assertThat(argument2.getValue()).isEqualTo(Set.of(1L));
  }

  @Test
  @WithMockUser
  void updateAccidentWhenNotFoundShouldRedirectTo404() throws Exception {
    when(service.update(ArgumentMatchers.any(AccidentEditDto.class), ArgumentMatchers.anySet()))
        .thenReturn(false);

    mockMvc.perform(put("/api/accidents/1/edit")
            .param("id", "1")
            .param("name", "Name")
            .param("text", "Text")
            .param("address", "Address")
            .param("typeId", "1")
            .param("ruleIds", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/404"));
    ArgumentCaptor<AccidentEditDto> argument = ArgumentCaptor.forClass(AccidentEditDto.class);
    ArgumentCaptor<Set<Long>> argument2 = ArgumentCaptor.forClass(Set.class);
    verify(service).update(argument.capture(), argument2.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
    assertThat(argument2.getValue()).isEqualTo(Set.of(1L));
  }

  @Test
  @WithMockUser
  void createAccidentWhenSuccessShouldRedirectToViewPage() throws Exception {
    when(service.save(ArgumentMatchers.any(AccidentCreateDto.class), ArgumentMatchers.anySet()))
        .thenReturn(Optional.of(1L));

    mockMvc.perform(post("/api/accidents")
            .param("name", "Name")
            .param("text", "Text")
            .param("address", "Address")
            .param("typeId", "1")
            .param("ruleIds", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/api/accidents/1"));
    ArgumentCaptor<AccidentCreateDto> argument = ArgumentCaptor.forClass(AccidentCreateDto.class);
    ArgumentCaptor<Set<Long>> argument2 = ArgumentCaptor.forClass(Set.class);
    verify(service).save(argument.capture(), argument2.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
    assertThat(argument2.getValue()).isEqualTo(Set.of(1L));
  }

  @Test
  @WithMockUser
  void createAccidentWhenFailedShouldRedirectTo400() throws Exception {
    when(service.save(ArgumentMatchers.any(AccidentCreateDto.class), ArgumentMatchers.anySet()))
        .thenReturn(Optional.empty());

    mockMvc.perform(post("/api/accidents")
            .param("name", "Name")
            .param("text", "Text")
            .param("address", "Address")
            .param("typeId", "1")
            .param("ruleIds", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error/400"));

    ArgumentCaptor<AccidentCreateDto> argument = ArgumentCaptor.forClass(AccidentCreateDto.class);
    ArgumentCaptor<Set<Long>> argument2 = ArgumentCaptor.forClass(Set.class);
    verify(service).save(argument.capture(), argument2.capture());
    assertThat(argument.getValue().getName()).isEqualTo("Name");
    assertThat(argument2.getValue()).isEqualTo(Set.of(1L));
  }
}