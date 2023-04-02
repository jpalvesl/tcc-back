package com.tcc.joaomyrlla.appcode2know.dto;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO {

  private Long id;

  @NotNull(message = "O atributo data de abertura é obrigatório")
  private String dtAbertura;

  @NotNull(message = "O atributo data de encerramento é obrigatório")
  private String dtEncerramento;

  private String titulo;

  @NotBlank(message = "O atributo nome da turma é obrigatório")
  private String nomeTurma;

  @NotBlank(message = "O atributo semestre é obrigatório")
  private String Semestre;

  @NotNull
  private Long instituicaoId;

  public static TurmaDTO toTurma(Turma turma) {
    TurmaDTO turmaDTO = new TurmaDTO();
    BeanUtils.copyProperties(turma, turmaDTO);

    if (turma.getInstituicao() != null) {
      turmaDTO.setInstituicaoId(turma.getInstituicao().getId());
    }

    turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));
    turmaDTO.setDtAbertura(DateUtils.toPattern("yyyy-MM-dd", turma.getDtAbertura()));
    turmaDTO.setDtEncerramento(DateUtils.toPattern("yyyy-MM-dd", turma.getDtEncerramento()));

    return turmaDTO;
  }
}