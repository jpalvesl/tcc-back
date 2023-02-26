package com.tcc.joaomyrlla.appcode2know.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProblemaControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  IProblemaService problemaService;

  @Test
  void findAll() {}

  @Test
  void findById() {}

  @Test
  void add() {}

  @Test
  void edit() {}
  
  @Test
  void delete() {}
}
