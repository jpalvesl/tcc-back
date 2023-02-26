package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;


@SpringBootTest
@AutoConfigureMockMvc
public class InstituicaoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  IInstituicaoService instituicaoService;


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