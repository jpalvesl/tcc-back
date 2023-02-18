package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import com.tcc.joaomyrlla.appcode2know.repository.SubmissaoRepository;
import com.tcc.joaomyrlla.appcode2know.api.JuizOnlinePython;


@Service
public class SubmissaoServiceImpl implements ISubmissaoService {

  final
  SubmissaoRepository submissaoRepository;

  JuizOnlinePython juizOnlinePython = new JuizOnlinePython();

   public SubmissaoServiceImpl(SubmissaoRepository submissaoRepository) {
        this.submissaoRepository = submissaoRepository;
    }


  @Override
  public List<Submissao> findAll() {
    return submissaoRepository.findAll();
  }

  @Override
  public List<Submissao> findByProblemaId(Long problemaId) {
    List<Submissao> listaSubmissoesPorProblema = submissaoRepository.findAll().stream().filter(s -> s.getProblema().getId() == problemaId).collect(Collectors.toList());
    return listaSubmissoesPorProblema;
    
  }

  @Override
  public HashMap<String, Object> realizaSubmissao(Submissao submissao) throws IOException, InterruptedException {
    // TODO: Buscar casos de teste relacionados ao problema

    // TODO:  criar uma lista que guardara todas as respostas obtidas pela api em python

    // TODO: Realizar um for para realizar uma requisição a cada caso de teste e guardar o seu resultado no array citado acima

    // TODO: Criar um Array de Models Submissões que serão guardados no banco de dados, usando o id do problema, o id do usuario e por fim os dados obtidos da reposta do api

    return  juizOnlinePython.realizaSubmissao();
  }

}