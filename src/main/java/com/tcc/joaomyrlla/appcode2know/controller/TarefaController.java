package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TarefaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tarefa")
public class TarefaController {
    @Autowired
    ITarefaService tarefaService;

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<List<TarefaDTO>> findByAluno(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(tarefaService.findByAluno(usuarioId));
    }

    @GetMapping("/turma/{turma_id}")
    public ResponseEntity<List<TarefaDTO>> findByTurma(@PathVariable("turma_id") Long turmaId) {
        return ResponseEntity.ok().body(tarefaService.findByTurma(turmaId));
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> add(@Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.ok().body(tarefaService.add(tarefa));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<TarefaDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                          @Valid @RequestBody TarefaDTO tarefa) {
        return ResponseEntity.ok().body(tarefaService.edit(tarefa, usuarioId));
    }

    @DeleteMapping("/{id}/usuario/{usuario_id}")
    public void delete(@PathVariable("id") Long id,
                       @PathVariable("usuario_id") Long usuarioId) {
        tarefaService.delete(id, usuarioId);
    }

    @PostMapping("/{id}/problema/{problema_id}/usuario/{usuario_id}")
    public void addProblemaEmTarefa(@PathVariable("id") Long id,
                                    @PathVariable("problema_id") Long problema_id,
                                    @PathVariable("usuario_id") Long usuarioId) {
        tarefaService.addProblemaEmTarefa(problema_id, id, usuarioId);
    }
}
