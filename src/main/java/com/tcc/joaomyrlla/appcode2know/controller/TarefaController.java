package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.Tarefa;
import com.tcc.joaomyrlla.appcode2know.service.ITarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TarefaController {
    @Autowired
    ITarefaService tarefaService;

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> findByAluno(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(tarefaService.findByAluno(usuarioId));
    }

    @GetMapping("/turma/{turma_id}")
    public ResponseEntity<Object> findByTurma(@PathVariable("turma_id") Long turmaId) {
        return ResponseEntity.ok().body(tarefaService.findByTurma(turmaId));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Tarefa tarefa) {
        return ResponseEntity.ok().body(tarefaService.add(tarefa));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody Tarefa tarefa) {
        return ResponseEntity.ok().body(tarefaService.edit(tarefa, usuarioId));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        tarefaService.delete(id);
    }
}
