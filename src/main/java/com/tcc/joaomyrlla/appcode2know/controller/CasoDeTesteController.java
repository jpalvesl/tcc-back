package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("caso_teste")
public class CasoDeTesteController {
    @Autowired
    ICasoDeTesteService casoDeTesteService;

    @GetMapping("/submissao/{submissao_id}")
    public ResponseEntity<Object> findBySubmissao(@PathVariable("submissao_id") Long submissaoId) {
        return ResponseEntity.ok().body(casoDeTesteService.findBySubmissao(submissaoId));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody CasoDeTeste casoDeTeste) {
        return ResponseEntity.ok().body(casoDeTesteService.add(casoDeTeste));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<Object> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody CasoDeTeste casoDeTeste) {
        return ResponseEntity.ok().body(casoDeTesteService.edit(casoDeTeste));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        casoDeTesteService.delete(id);
    }
}
