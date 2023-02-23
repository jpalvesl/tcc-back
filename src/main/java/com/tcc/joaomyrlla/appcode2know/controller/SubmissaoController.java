package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping("/submissao")
public class SubmissaoController {
    @Autowired
    ISubmissaoService submissaoService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/problema/{problema_id}")
    public ResponseEntity<Object> findByProblemaId(@PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok(problemaId);
    }

    @PostMapping("/problema/{problema_id}/usuario/{usuario_id}")
    public HashMap<String, Object> realizaSubmissao(@RequestBody Submissao submissao, @PathVariable("problema_id") Long problemaId, @PathVariable("usuario_id") Long usuarioId) throws IOException, InterruptedException {
        return submissaoService.realizaSubmissao(submissao);
    }
}