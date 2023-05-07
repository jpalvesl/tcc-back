package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.EditSenhaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;

    @GetMapping("/instituicao/{instituicao_id}")
    public ResponseEntity<List<UsuarioDTO>> findByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(usuarioService.findByInstituicao(instituicaoId));
    }

    @GetMapping("/aluno/instituicao/{instituicao_id}")
    public ResponseEntity<List<UsuarioDTO>> findAlunoByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(usuarioService.findAlunoByInstituicao(instituicaoId));
    }

    @GetMapping("/professor/instituicao/{instituicao_id}")
    public ResponseEntity<List<UsuarioDTO>> findProfessorByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(usuarioService.findProfessorByInstituicao(instituicaoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findByUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    @GetMapping("/turma/{turma_id}")
    public ResponseEntity<List<UsuarioDTO>> findByTurma(@PathVariable("turma_id") Long turmaId) {
        return ResponseEntity.ok().body(usuarioService.findByTurma(turmaId));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.add(usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> edit(@PathVariable("id") Long id,
                                           @Valid @RequestBody UsuarioDTO usuario) {
        return ResponseEntity.ok().body(usuarioService.edit(usuario));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
    }

    @PutMapping("/{usuario_id}/editar_senha")
    public void editarSenha(@PathVariable("usuario_id") Long usuarioId,
                            @RequestBody EditSenhaDTO editSenhaDTO) {
        usuarioService.editarSenha(usuarioId, editSenhaDTO);
    }

    @PutMapping("/adicionar_cargo/{cargo}/administrador/{administrador_id}")
    public void adicionarCargo(@PathVariable("cargo") String cargo,
                               @PathVariable("administrador_id") Long administradorId,
                               @RequestBody List<Long> listaIds) {
        if (!cargo.equals("professor") && !cargo.equals("administrador")) {
            throw new RuntimeException("Tipo de cargo não encontrado");
        }

        usuarioService.cadastrarCargo(cargo, administradorId, listaIds);
    }
}
