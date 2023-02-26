package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;

import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<Object> add(@RequestBody ProblemaDTO problema) {
        Problema novoProblema = new Problema();
        BeanUtils.copyProperties(problema, novoProblema);

        return ResponseEntity.ok().body(problemaService.add(novoProblema));
    }

   @PatchMapping("/usuario/{usuario_id}")
   ResponseEntity<Object> edit(@RequestBody ProblemaDTO problema,
                               @PathVariable("usuario_id") Long usuarioId) {
       
        Problema problemaEditado = new Problema();
        BeanUtils.copyProperties(problema, problemaEditado);

        return ResponseEntity.ok().body(problemaService.edit(problemaEditado, usuarioId));
   }

   @DeleteMapping("/{id}/usuario/{criador_id}")
   public void delete(@PathVariable("id") Long id, @PathVariable("criador_id") Long criadorId) {
       problemaService.delete(id,criadorId);
   }
}
