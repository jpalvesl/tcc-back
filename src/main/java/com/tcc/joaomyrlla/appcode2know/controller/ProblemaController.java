package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.service.IProblemaService;
import com.tcc.joaomyrlla.appcode2know.dto.ProblemaDTO;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/tarefa/{tarefa_id}")
    public ResponseEntity<List<ProblemaDTO>> findByTarefa(@PathVariable("tarefa_id") Long tarefaId) {
        return ResponseEntity.ok().body(problemaService.findByTarefa(tarefaId));
    }

    @GetMapping("/tarefa/{tarefa_id}/usuario/{usuario_id}")
    public ResponseEntity<List<ProblemaDTO>> findByTarefaAndUsuario(@PathVariable("tarefa_id") Long tarefaId,
                                                                    @PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(problemaService.findByTarefaAndUsusario(tarefaId, usuarioId));
    }

    @PostMapping
    public ResponseEntity<ProblemaDTO> add(@Valid @RequestBody ProblemaDTO problema) {
        return ResponseEntity.status(HttpStatus.CREATED).body(problemaService.add(problema));
    }

    @PatchMapping("/usuario/{usuario_id}")
    ResponseEntity<ProblemaDTO> edit(@Valid @RequestBody ProblemaDTO problema,
                                     @PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(problemaService.edit(problema, usuarioId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/usuario/{criador_id}")
    public void delete(@PathVariable("id") Long id, @PathVariable("criador_id") Long criadorId) {
        problemaService.delete(id, criadorId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{problema_id}/topico/{topico_id}")
    public void addTopicoEmProblema(@PathVariable("problema_id") Long problemaId,
                                    @PathVariable("topico_id") Long topicoId) {
        problemaService.addTopicoEmProblema(topicoId, problemaId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{problema_id}/topico/{topico_id}")
    public void removerTopicoEmProblema(@PathVariable("problema_id") Long problemaId,
                                        @PathVariable("topico_id") Long topicoId) {
        problemaService.removerTopicoEmProblema(topicoId, problemaId);
    }

    @GetMapping("/usuarioBuscado/{usuario_id}")
    public ResponseEntity findProblemasTentadosEResolvidos(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(problemaService.findProblemasTentadosEResolvidos(usuarioId));
    }

    @GetMapping("/tentados/usuario/{usuario_id}")
    public ResponseEntity<List<ProblemaDTO>> findProblemasTentados(@PathVariable("usuario_id") Long usuarioId) {
        return ResponseEntity.ok().body(problemaService.findProblemasTentados(usuarioId));
    }
}
