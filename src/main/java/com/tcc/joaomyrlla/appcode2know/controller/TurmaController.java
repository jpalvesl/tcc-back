package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("turma")
public class TurmaController {
    @Autowired
    ITurmaService turmaService;

    @GetMapping("/instituicao/{instituicao_id}")
    public ResponseEntity<TurmaDTO> findByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(turmaService.findByInstituicao(instituicaoId));
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<TurmaDTO> findByUsuario(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(turmaService.findByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> add(@RequestBody TurmaDTO turma) {
        Turma novaTurma = new Turma();
        BeanUtils.copyProperties(turma, novaTurma);

        return ResponseEntity.ok().body(turmaService.add(novaTurma));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<TurmaDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody TurmaDTO turma) {

        Turma turmaEditada = new Turma();
        BeanUtils.copyProperties(turma, turmaEditada);

        return ResponseEntity.ok().body(turmaService.edit(turmaEditada, usuarioId));

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        turmaService.delete(id);
    }
}
