package com.tcc.joaomyrlla.appcode2know.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.RespostaCasoTeste;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.RespostaCasoDeTesteRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import com.tcc.joaomyrlla.appcode2know.repository.SubmissaoRepository;
import com.tcc.joaomyrlla.appcode2know.api.JuizOnlinePython;


@Service
public class SubmissaoServiceImpl implements ISubmissaoService {

    @Autowired
    private ICasoDeTesteService casoDeTesteService;

    @Autowired
    private RespostaCasoDeTesteRepository respostaCasoDeTesteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    final
    SubmissaoRepository submissaoRepository;


    JuizOnlinePython juizOnlinePython = new JuizOnlinePython();

    public SubmissaoServiceImpl(SubmissaoRepository submissaoRepository) {
        this.submissaoRepository = submissaoRepository;
    }


    @Override
    public List<SubmissaoDTO> findAll() {
        return submissaoRepository.findAll().stream()
                .map(SubmissaoDTO::toSubmissaoDTO)
                .toList();
    }

    public List<SubmissaoDTO> findByAluno(Long alunoId) {
        Usuario usuario = usuarioRepository.findById(alunoId).orElseThrow(UsuarioNotFoundException::new);

        return submissaoRepository.findAll()
                .stream()
                .filter(submissao -> submissao.getUsuario().getId().equals(alunoId))
                .map(SubmissaoDTO::toSubmissaoDTO)
                .toList();
    }

    @Override
    public List<SubmissaoDTO> findByProblemaId(Long problemaId) {
        return submissaoRepository.findAll()
                .stream()
                .filter(submissao -> submissao.getProblema().getId().equals(problemaId))
                .map(SubmissaoDTO::toSubmissaoDTO)
                .toList();
    }

    @Override
    public List<RespostaDeCasoTesteDTO> realizaSubmissao(SubmissaoDTO submissaoDTO) {
        List<CasoDeTesteDTO> casosDeTeste = casoDeTesteService.findByProblema(submissaoDTO.getProblemaId());

        Submissao submissao = new Submissao();
        Problema problema = new Problema();
        problema.setId(submissaoDTO.getProblemaId());

        Usuario usuario = new Usuario();
        usuario.setId(submissaoDTO.getUsuarioId());

        submissao.setCodigoResposta(submissaoDTO.getCodigoResposta());
        submissao.setProblema(problema);
        submissao.setUsuario(usuario);

        submissaoRepository.save(submissao);

        List<RespostaDeCasoTesteDTO> retorno = casosDeTeste.stream()
                .map(casoTeste -> {
                    HashMap<String, Object> request = new HashMap<>();
                    request.put("codigoResposta", submissaoDTO.getCodigoResposta());
                    request.put("entradas", casoTeste.getEntrada());

                    HashMap<String, Object> responseOnlineJudge;
                    RespostaDeCasoTesteDTO respostaDeCasoTesteDto = new RespostaDeCasoTesteDTO();
                    respostaDeCasoTesteDto.setSubmissaoId(submissao.getId());
                    respostaDeCasoTesteDto.setCaso(casoTeste.getCaso());

                    try {
                        responseOnlineJudge = juizOnlinePython.realizaSubmissao(request);
                        respostaDeCasoTesteDto.setEntrada((String) responseOnlineJudge.get("entrada"));
                        respostaDeCasoTesteDto.setSaida((String) responseOnlineJudge.get("saida"));
                        respostaDeCasoTesteDto.setTempo((double) responseOnlineJudge.get("tempo"));
                        respostaDeCasoTesteDto.setStatus((String) responseOnlineJudge.get("status"));

                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return respostaDeCasoTesteDto;
                })
                .toList();

        retorno.forEach(resposta -> {
            RespostaCasoTeste respostaCasoTeste = new RespostaCasoTeste();
            BeanUtils.copyProperties(resposta, respostaCasoTeste);
            respostaCasoTeste.setSubmissao(submissao);

            respostaCasoDeTesteRepository.save(respostaCasoTeste);
        });

        return retorno;
    }
}