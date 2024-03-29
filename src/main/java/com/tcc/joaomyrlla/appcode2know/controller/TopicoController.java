package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TopicoDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topico")
public class TopicoController {
    @Autowired
    ITopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> findAll() {
        return ResponseEntity.ok().body(topicoService.findAll());
    }

    @GetMapping("/problema/{problema_id}")
    public ResponseEntity<List<TopicoDTO>> findByProblema(@PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok().body(topicoService.findByProblema(problemaId));
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> add(@Valid @RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicoService.add(topicoDTO));
    }

    @PatchMapping
    public ResponseEntity<TopicoDTO> edit(@Valid @RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok().body(topicoService.edit(topicoDTO));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{topico_id}")
    public void delete(@PathVariable("topico_id") Long topicoId) {
        topicoService.delete(topicoId);
    }
}
