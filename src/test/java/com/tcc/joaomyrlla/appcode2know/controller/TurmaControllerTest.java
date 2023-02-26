package com.tcc.joaomyrlla.appcode2know.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;

@SpringBootTest
@AutoConfigureMockMvc
public class TurmaControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ITurmaService turmaService;

  @Test
  void findByInstituicao() {}

  @Test
  void findByUsuario() {}

  @Test
  void add() {}

  @Test
  void edit() {}

  @Test
  void delete() {}
}
