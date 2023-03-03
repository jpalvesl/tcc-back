package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;

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
                                       @RequestBody TurmaDTO turma) {

        return ResponseEntity.ok().body(turmaService.edit(turma, usuarioId));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        turmaService.delete(id);
    }
}
