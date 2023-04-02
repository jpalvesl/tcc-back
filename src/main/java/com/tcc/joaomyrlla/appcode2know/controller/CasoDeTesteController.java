package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("caso_teste")
public class CasoDeTesteController {
    @Autowired
    ICasoDeTesteService casoDeTesteService;

    @GetMapping("/submissao/{submissao_id}")
    public ResponseEntity<List<CasoDeTesteDTO>> findBySubmissao(@PathVariable("submissao_id") Long submissaoId) {
        return ResponseEntity.ok().body(casoDeTesteService.findBySubmissao(submissaoId));
    }

    @GetMapping("/problema/{problema_id}")
    public ResponseEntity<List<CasoDeTesteDTO>> findByProblema(@PathVariable("problema_id") Long problemaId) {
        return ResponseEntity.ok().body(casoDeTesteService.findByProblema(problemaId));
    }

    @PostMapping("/problema/{problema_id}/criador/{criador_id}")
    public ResponseEntity<CasoDeTesteDTO> add(@PathVariable("problema_id") Long problemaId,
                                              @PathVariable("criador_id") Long criadorId,
                                              @Valid @RequestBody CasoDeTesteDTO casoDeTeste) {
        return ResponseEntity.status(HttpStatus.CREATED).body(casoDeTesteService.add(casoDeTeste, problemaId, criadorId));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<CasoDeTesteDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                               @Valid @RequestBody CasoDeTesteDTO casoDeTeste) {
        return ResponseEntity.ok().body(casoDeTesteService.edit(casoDeTeste, usuarioId));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/criador_id/{criador_id}")
    public void delete(@PathVariable("id") Long id,
                       @PathVariable("criador_id") Long criadorId) {
        casoDeTesteService.delete(id, criadorId);
    }
}
