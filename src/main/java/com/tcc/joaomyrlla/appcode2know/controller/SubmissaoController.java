package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/submissao")
public class SubmissaoController {
    @Autowired
    ISubmissaoService submissaoService;

    @GetMapping
    public ResponseEntity<List<SubmissaoDTO>> findAll() {
        return ResponseEntity.ok().body(submissaoService.findAll());
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<List<SubmissaoDTO>> findByAluno(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(submissaoService.findByAluno(usuarioId));
    }

    @GetMapping("/problema/{problema_id}")
    public ResponseEntity<List<SubmissaoDTO>> findByProblemaId(@PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok().body(submissaoService.findByProblemaId(problemaId));
    }

    @GetMapping("/usuario/{usuario_id}/problema/{problema_id}")
    public ResponseEntity findByUsuarioAndProblema(@PathVariable("usuario_id") Long usuarioId,
                                                   @PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok().body(submissaoService.findByUsusarioAndProblema(usuarioId, problemaId));
    }

    @PostMapping("/problema/{problema_id}/usuario/{usuario_id}")
    public List<RespostaDeCasoTesteDTO> realizaSubmissao(@Valid @RequestBody SubmissaoDTO submissao,
                                                         @PathVariable("problema_id") Long problemaId,
                                                         @PathVariable("usuario_id") Long usuarioId) throws IOException, InterruptedException {
        submissao.setProblemaId(problemaId);
        submissao.setUsuarioId(usuarioId);

        return submissaoService.realizaSubmissao(submissao);
    }
}
