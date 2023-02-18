package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("problema")
public class ProblemaController {
    @Autowired
    IProblemaService problemaService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok().body(problemaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(problemaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Problema problema) { // mudar pra DTO depois
        return ResponseEntity.ok().body(problemaService.add(problema));
    }

   @DeleteMapping("/{id}/usuario/{criadorId}")
   public ResponseEntity<Object> delete(@PathVariable("id") Long id, @PathVariable("criadorId") Long criadorId) {
       problemaService.delete(id,criadorId);
       return ResponseEntity.ok().build();
   }

   @PatchMapping("/usuario/{usuario_id}")
   ResponseEntity<Object> edit(@RequestBody Problema problema, @PathVariable("usuario_id") Long usuarioId) { // mudar para DTO problema depois
       return ResponseEntity.ok().body(problemaService.edit(problema, usuarioId));
   }
}
