package com.tcc.joaomyrlla.appcode2know.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituicaoDTO {

  private Long id;

  @NotBlank(message = "O atributo nome é obrigatório")
  private String nome;

  @NotBlank(message = "O atributo campus é obrigatório")
  private String campus;

}