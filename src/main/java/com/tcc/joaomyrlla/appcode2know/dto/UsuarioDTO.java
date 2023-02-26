package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
  private Long id;

  @NotBlank(message = "O atributo ehAdm é obrigatório")
  private boolean ehAdm;

  @NotBlank(message = "O atributo ehProfessor é obrigatório")
  private boolean ehProfessor;

  @NotBlank(message = "O atributo ehProfessor é obrigatório")
  private String email;

  
  @NotBlank(message = "O atributo nome é obrigatório")
  private String nome;

  @NotBlank(message = "O atributo senha é obrigatório")
  private String senha;
  
  @NotBlank(message = "O atributo usuario é obrigatório")
  private String usuario;
  
  private String instituicaoAtualId;
}