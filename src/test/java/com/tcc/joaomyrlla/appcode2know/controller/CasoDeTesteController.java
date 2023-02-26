package com.tcc.joaomyrlla.appcode2know.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;

@SpringBootTest
@AutoConfigureMockMvc
public class CasoDeTesteController {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ICasoDeTesteService casoDeTesteService;

  @Test
  void findBySubmissao() {}

  @Test
  void add() {}

  @Test
  void edit() {}

  @Test
  void delete() {}
}
