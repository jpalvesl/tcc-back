package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  IUsuarioService usuarioService;

  @Test
  void buscarUsuarios() throws Exception {
    UsuarioDTO usuario = new UsuarioDTO(1L, false, false, "teste@gmail.com", "teste", "12345", "user_teste", null);

    Mockito.when(usuarioService.findByInstituicao()).thenReturn(List.of(usuario));
    this.mockMvc.perform(get("/usuario"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"id\":1,\"nome\":\"teste\",\"email\":\"test@gmail.com\",\"usuario\":\"user_test\",\"senha\":\"12345\",\"ehAdm\":false,\"ehProfessor\":false,\"instituicaoAtual\":null,\"instituicoes\":null,\"turmas\":null}]"));
  }

  @Test
  void findByInstituicao() {}

  @Test
  void findByUsuario() {}

  @Test
  void findByTurma() {}

  @Test
  void cadastrar() {}

  @Test
  void edit() {}

  @Test
  void delete() {}
}
