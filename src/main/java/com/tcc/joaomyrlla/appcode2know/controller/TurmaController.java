package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("turma")
public class TurmaController {
    @Autowired
    ITurmaService turmaService;

    @GetMapping("/instituicao/{instituicao_id}")
    public ResponseEntity<Object> findByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(turmaService.findByInstituicao(instituicaoId));
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> findByUsuario(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(turmaService.findByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Turma turma) {
        return ResponseEntity.ok().body(turmaService.add(turma));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody Turma turma) {
        return ResponseEntity.ok().body(turmaService.edit(turma, usuarioId));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        turmaService.delete(id);
    }
}
