package com.tcc.joaomyrlla.appcode2know.controller;

import com.tcc.joaomyrlla.appcode2know.model.SubmissaoModel;
import com.tcc.joaomyrlla.appcode2know.repository.SubmissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SubmissaoController {
    @Autowired
    SubmissaoRepository submissaoRepository;

    @GetMapping("poc_submissao")
    public boolean pocSubmissao() {
        // TODO: Ler do banco de dados as repostas e guardar em um mapa
        Optional<SubmissaoModel> respostas = submissaoRepository.findById(1L);

        // TODO: Guardar em um mapa e organizar pela linha (coluna do banco)

        // TODO: Ler de um arquivo as saídas e a partir disso comparar com os valores do banco de dados


        return true;
    }
}
