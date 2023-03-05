package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turma")
public class TurmaController {
    @Autowired
    ITurmaService turmaService;

    @GetMapping("/instituicao/{instituicao_id}")
    public ResponseEntity<List<TurmaDTO>> findByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(turmaService.findByInstituicao(instituicaoId));
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<List<TurmaDTO>> findByUsuario(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(turmaService.findByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> add(@RequestBody TurmaDTO turma) {
        return ResponseEntity.ok().body(turmaService.add(turma));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<TurmaDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                         @Valid @RequestBody TurmaDTO turma) {

        return ResponseEntity.ok().body(turmaService.edit(turma, usuarioId));

    }

    @DeleteMapping("/{id}/usuario/{usuario_id}")
    public void delete(@PathVariable("id") Long id, Long professorId) {
        turmaService.delete(id, professorId);
    }

    @PostMapping("/{id}/aluno/{aluno_id}/criador/{criador_id}")
    public void addAlunoEmTurma(@PathVariable("id") Long id,
                                @PathVariable("aluno_id") Long alunoId,
                                @PathVariable("criador_id") Long criadorId) {
        turmaService.addAlunoEmTurma(id, alunoId, criadorId);
    }
}
