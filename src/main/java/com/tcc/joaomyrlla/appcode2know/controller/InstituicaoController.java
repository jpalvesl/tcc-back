package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.BeanUtils;

@RestController
@RequestMapping("instituicao")
public class InstituicaoController {
    @Autowired
    IInstituicaoService instituicaoService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(instituicaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(instituicaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody InstituicaoDTO instituicao) {

        Instituicao novaInstituicao = new Instituicao();
        BeanUtils.copyProperties(instituicao, novaInstituicao);

        return ResponseEntity.ok().body(instituicaoService.add(novaInstituicao));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody InstituicaoDTO instituicao) {

        Instituicao instituicaoEditada = new Instituicao();
        BeanUtils.copyProperties(instituicao, instituicaoEditada);

        return ResponseEntity.ok().body(instituicaoService.edit(instituicaoEditada, usuarioId));
    }

    @DeleteMapping("/{id}/usuario/{usuario_id}")
    public void delete(@PathVariable("id") Long id,
                       @PathVariable("usuario_id") Long usuarioId) {
        instituicaoService.delete(id, usuarioId);
    }
}
