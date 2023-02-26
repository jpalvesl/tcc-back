package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<UsuarioDTO> findByInstituicao() {
        return ResponseEntity.ok().body(usuarioService.findByInstituicao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findByUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    @GetMapping("/turma/{turma_id}")
    public ResponseEntity<UsuarioDTO> findByTurma(@PathVariable("turma_id") Long turmaId) {
        return ResponseEntity.ok().body(usuarioService.findByTurma(turmaId));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuario) {
        Usuario novoUsuario = new Usuario();
        BeanUtils.copyProperties(usuario, novoUsuario);

        return ResponseEntity.ok().body(usuarioService.add(novoUsuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> edit(@PathVariable("id") Long id,
                                       @RequestBody UsuarioDTO usuario) {
        Usuario novoUsuario = new Usuario();
        BeanUtils.copyProperties(usuario, novoUsuario);
                                        
        return ResponseEntity.ok().body(usuarioService.edit(novoUsuario));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
    }
}
