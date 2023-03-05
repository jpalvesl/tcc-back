package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
  private Long id;

  private boolean ehAdm;

  private boolean ehProfessor;

  @NotBlank(message = "O atributo ehProfessor é obrigatório")
  private String email;

  
  @NotBlank(message = "O atributo nome é obrigatório")
  private String nome;

  @NotBlank(message = "O atributo senha é obrigatório")
  private String senha;
  
  @NotBlank(message = "O atributo usuario é obrigatório")
  private String usuario;
  
  private Long instituicaoAtualId;
}