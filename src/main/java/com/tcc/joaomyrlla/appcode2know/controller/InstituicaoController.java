package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.BeanUtils;

import java.util.List;

@RestController
@RequestMapping("instituicao")
public class InstituicaoController {
    @Autowired
    IInstituicaoService instituicaoService;

    @GetMapping
    public ResponseEntity<List<InstituicaoDTO>> findAll() {
        return ResponseEntity.ok().body(instituicaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(instituicaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InstituicaoDTO> add(@RequestBody InstituicaoDTO instituicao) {


        return ResponseEntity.ok().body(instituicaoService.add(instituicao));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<InstituicaoDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody InstituicaoDTO instituicao) {

        return ResponseEntity.ok().body(instituicaoService.edit(instituicao, usuarioId));
    }

    @DeleteMapping("/{id}/usuario/{usuario_id}")
    public void delete(@PathVariable("id") Long id,
                       @PathVariable("usuario_id") Long usuarioId) {
        instituicaoService.delete(id, usuarioId);
    }
}
