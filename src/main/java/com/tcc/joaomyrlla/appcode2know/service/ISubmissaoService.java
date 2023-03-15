package com.tcc.joaomyrlla.appcode2know.service;

import java.io.IOException;
import java.util.List;

import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;

public interface ISubmissaoService {
  public List<SubmissaoDTO> findAll();
  public List<SubmissaoDTO> findByProblemaId(Long id);
  public List<RespostaDeCasoTesteDTO> realizaSubmissao(SubmissaoDTO submissao) throws IOException, InterruptedException;
}