package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("problema")
public class ProblemaController {
    @Autowired
    IProblemaService problemaService;

    @GetMapping
    public ResponseEntity<List<ProblemaDTO>> findAll() {
        return ResponseEntity.ok().body(problemaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemaDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(problemaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProblemaDTO> add(@RequestBody ProblemaDTO problema) {
        return ResponseEntity.ok().body(problemaService.add(problema));
    }

    @PatchMapping("/usuario/{usuario_id}")
    ResponseEntity<ProblemaDTO> edit(@RequestBody ProblemaDTO problema,
                                     @PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(problemaService.edit(problema, usuarioId));
    }

    @DeleteMapping("/{id}/usuario/{criador_id}")
    public void delete(@PathVariable("id") Long id, @PathVariable("criador_id") Long criadorId) {
        problemaService.delete(id, criadorId);
    }
}
