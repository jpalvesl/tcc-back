package com.tcc.joaomyrlla.appcode2know.dto;

import java.sql.Date;

import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;

    @NotNull(message = "O atributo data de abertura é obrigatório")
    private String dtAbertura;

    @NotNull(message = "O atributo data de encerramento é obrigatório")
    private String dtEncerramento;

    @NotNull(message = "O atributo ehProva é obrigatório")
    private boolean ehProva;

    @NotBlank(message = "O atributo descrição é obrigatório")
    private String descricao;

    @NotBlank(message = "O atributo título é obrigatório")
    private String titulo;

    @NotNull
    private Long criadorId;

    @NotNull
    private Long turmaId;

    private int qtdProblemas;

    public static TarefaDTO toTarefaDTO(Tarefa tarefa) {
        TarefaDTO tarefaDTO = new TarefaDTO();
        BeanUtils.copyProperties(tarefa, tarefaDTO);

        tarefaDTO.setDtAbertura(DateUtils.toPattern("yyyy-MM-dd", tarefa.getDtAbertura()));
        tarefaDTO.setDtEncerramento(DateUtils.toPattern("yyyy-MM-dd", tarefa.getDtEncerramento()));
        tarefaDTO.setTurmaId(tarefa.getTurma().getId());
        tarefaDTO.setCriadorId(tarefa.getCriador().getId());
        tarefaDTO.setQtdProblemas(tarefa.getProblemas().size());

        return tarefaDTO;
    }
}