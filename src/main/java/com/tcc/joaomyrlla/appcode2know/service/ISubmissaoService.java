package com.tcc.joaomyrlla.appcode2know.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;

public interface ISubmissaoService {
  public List<Submissao> findAll();
  public List<Submissao> findByProblemaId(Long id);
  public HashMap<String, Object> realizaSubmissao(Submissao submissao) throws IOException, InterruptedException;
}