package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.model.CasoDeTeste;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("caso_teste")
public class CasoDeTesteController {
    @Autowired
    ICasoDeTesteService casoDeTesteService;

    @GetMapping("/submissao/{submissao_id}")
    public ResponseEntity<CasoDeTesteDTO> findBySubmissao(@PathVariable("submissao_id") Long submissaoId) {
        return ResponseEntity.ok().body(casoDeTesteService.findBySubmissao(submissaoId));
    }

    @PostMapping
    public ResponseEntity<CasoDeTesteDTO> add(@RequestBody CasoDeTesteDTO casoDeTeste) {
        CasoDeTeste novoCasoDeTeste = new CasoDeTeste();
        BeanUtils.copyProperties(casoDeTeste, novoCasoDeTeste);

        return ResponseEntity.ok().body(casoDeTesteService.add(novoCasoDeTeste));
    }

    @PatchMapping("/usuario/{usuario_id}")
    public ResponseEntity<CasoDeTesteDTO> edit(@PathVariable("usuario_id") Long usuarioId,
                                       @RequestBody CasoDeTesteDTO casoDeTeste) {
        
        CasoDeTeste casoDeTesteEditado = new CasoDeTeste();
        BeanUtils.copyProperties(casoDeTeste, casoDeTesteEditado);
        
        return ResponseEntity.ok().body(casoDeTesteService.edit(casoDeTesteEditado));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        casoDeTesteService.delete(id);
    }
}
