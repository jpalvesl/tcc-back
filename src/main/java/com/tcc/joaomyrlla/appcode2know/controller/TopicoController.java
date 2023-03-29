package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("topico")
public class TopicoController {
    @Autowired
    ITopicoService topicoService;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok().body(topicoService.findAll());
    }

    @GetMapping("/problema/{problema_id}")
    public ResponseEntity findByProblema(@PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok().body(topicoService.findByProblema(problemaId));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok().body(topicoService.add(topicoDTO));
    }

    @PatchMapping
    public ResponseEntity edit(@RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok().body(topicoService.edit(topicoDTO));
    }

    @DeleteMapping("/{topico_id}")
    public void delete(@PathVariable("topico_id") Long topicoId) {
        topicoService.delete(topicoId);
    }
}
