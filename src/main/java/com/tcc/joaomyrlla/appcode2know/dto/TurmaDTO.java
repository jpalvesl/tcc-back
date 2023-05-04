package com.tcc.joaomyrlla.appcode2know.dto;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

  private String instituicaoTitulo;

  private String chave;

  private List<HashMap<String, Object>> professores = new ArrayList<>();

  private List<HashMap<String, Object>> monitores = new ArrayList<>();;

  public static TurmaDTO toTurma(Turma turma) {
    TurmaDTO turmaDTO = new TurmaDTO();
    BeanUtils.copyProperties(turma, turmaDTO);

    if (turma.getInstituicao() != null) {
      turmaDTO.setInstituicaoId(turma.getInstituicao().getId());
      turmaDTO.setInstituicaoTitulo(turma.getInstituicao().getTitulo());
    }

    turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));
    turmaDTO.setDtAbertura(DateUtils.toPattern("yyyy-MM-dd", turma.getDtAbertura()));
    turmaDTO.setDtEncerramento(DateUtils.toPattern("yyyy-MM-dd", turma.getDtEncerramento()));

    turma.getProfessores().forEach(professor -> {
      HashMap<String, Object> mapProfessor = new HashMap<>();
      mapProfessor.put("id", professor.getId());
      mapProfessor.put("nome", professor.getNome());

      turmaDTO.getProfessores().add(mapProfessor);
    });

    turma.getMonitores().forEach(monitor -> {
      HashMap<String, Object> mapMonitor = new HashMap<>();
      mapMonitor.put("id", monitor.getId());
      mapMonitor.put("nome", monitor.getNome());

      turmaDTO.getMonitores().add(mapMonitor);
    });

    return turmaDTO;
  }
}