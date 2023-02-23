package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<Object> findByInstituicao() {
        return ResponseEntity.ok().body(usuarioService.findByInstituicao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    @GetMapping("/turma/{turma_id}")
    public ResponseEntity<Object> findByTurma(@PathVariable("turma_id") Long turmaId) {
        return ResponseEntity.ok().body(usuarioService.findByTurma(turmaId));
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioService.add(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") Long id,
                                       @RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioService.edit(usuario));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
    }
}
