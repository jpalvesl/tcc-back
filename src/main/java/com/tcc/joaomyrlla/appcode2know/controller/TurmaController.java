package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("turma")
public class TurmaController {
    @Autowired
    ITurmaService turmaService;

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(turmaService.findById(id));
    }

    @GetMapping("/instituicao/{instituicao_id}")
    public ResponseEntity<List<TurmaDTO>> findByInstituicao(@PathVariable("instituicao_id") Long instituicaoId) {
        return ResponseEntity.ok().body(turmaService.findByInstituicao(instituicaoId));
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<Map<String, List<TurmaDTO>>> findByUsuario(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(turmaService.findByUsuario(usuarioId));
    }

    @PostMapping("/criador/{criador_id}")
    public ResponseEntity<TurmaDTO> add(@Valid @RequestBody TurmaDTO turma,
                                        @PathVariable("criador_id") Long criadorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.add(turma, criadorId));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<TurmaDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                         @Valid @RequestBody TurmaDTO turma) {
        return ResponseEntity.ok().body(turmaService.edit(turma, usuarioId));

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/usuario/{usuario_id}")
    public void delete(@PathVariable("id") Long id,
                       @PathVariable("usuario_id") Long professorId) {
        turmaService.delete(id, professorId);
    }

    @PostMapping("/{id}/aluno/{aluno_id}/criador/{criador_id}")
    public void addAlunoEmTurma(@PathVariable("id") Long id,
                                @PathVariable("aluno_id") Long alunoId,
                                @PathVariable("criador_id") Long criadorId) {
        turmaService.addAlunoEmTurma(id, alunoId, criadorId);
    }

    @DeleteMapping("/{id}/aluno/{aluno_id}/criador/{criador_id}")
    public void removerAlunoDaTurma(@PathVariable("id") Long id,
                                    @PathVariable("aluno_id") Long alunoId,
                                    @PathVariable("criador_id") Long criadorId) {
        turmaService.removerAlunoDaTurma(id, alunoId, criadorId);
    }

    @PostMapping("/{id}/professor/{professor_id}/professor_adicionado/{professor_adicionado_id}")
    public void addProfessorEmTurma(@PathVariable("id") Long turmaId,
                                    @PathVariable("professor_id") Long professorId,
                                    @PathVariable("professor_adicionado_id") Long professorAdicionadoId) {
        turmaService.addProfessorEmTurma(turmaId, professorId, professorAdicionadoId);
    }

    @DeleteMapping("/{id}/professor/{professor_id}/professor_adicionado/{professor_adicionado_id}")
    public void removerProfessorDaTurma(@PathVariable("id") Long turmaId,
                                        @PathVariable("professor_id") Long professorId,
                                        @PathVariable("professor_adicionado_id") Long professorAdicionadoId) {
        turmaService.removerProfessorDaTurma(turmaId, professorId, professorAdicionadoId);
    }

    @PostMapping("/entrar_em_turma/usuario/{usuario_id}")
    public void entrarEmTurma(@PathVariable("usuario_id") Long usuarioId,
                              @RequestBody Map<String, String> body) {
        String chave = (String) body.get("chave");

        turmaService.entrarEmTurma(usuarioId, chave);
    }
}
