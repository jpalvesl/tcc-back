package com.tcc.joaomyrlla.appcode2know.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;

@SpringBootTest
@AutoConfigureMockMvc

public class TarefaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ITarefaService tarefaService;

  @Test
  void findByAluno() {}

  @Test
  void findByTurma() {}

  @Test
  void add() {}

  @Test
  void edit() {}

  @Test
  void delete() {}

}