package com.tcc.joaomyrlla.appcode2know.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;


@SpringBootTest
@AutoConfigureMockMvc
public class SubmissaoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ISubmissaoService submissaoService;

  @Test
  void findByAluno() {}

  @Test
  void findByProblemaId() {}

  @Test
  void realizaSubmissao() {}

}