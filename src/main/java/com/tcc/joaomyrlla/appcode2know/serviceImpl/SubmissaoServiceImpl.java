package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.service.ICasoDeTesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import com.tcc.joaomyrlla.appcode2know.service.ISubmissaoService;
import com.tcc.joaomyrlla.appcode2know.repository.SubmissaoRepository;
import com.tcc.joaomyrlla.appcode2know.api.JuizOnlinePython;


@Service
public class SubmissaoServiceImpl implements ISubmissaoService {

    @Autowired
    ICasoDeTesteService casoDeTesteService;

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
        List<Submissao> listaSubmissoesPorProblema = submissaoRepository.findAll()
                .stream()
                .filter(submissao -> submissao.getProblema().getId().equals(problemaId))
                .toList();
        return listaSubmissoesPorProblema;
    }

    @Override
    public List<SubmissaoDTO> realizaSubmissao(SubmissaoDTO submissao) throws IOException, InterruptedException {
        // TODO: Buscar casos de teste relacionados ao problema
        List<CasoDeTesteDTO> casosDeTeste = casoDeTesteService.findByProblema(submissao.getProblemaId());

        // TODO:  criar uma lista que guardara todas as respostas obtidas pela api em python
        List<SubmissaoDTO> retorno = casosDeTeste.stream()
                .map(caso -> {
                    HashMap<String, Object> request = new HashMap<>();
                    request.put("codigoResposta", submissao.getCodigoResposta());
                    request.put("entradas", caso.getEntrada());

                    HashMap<String, Object> responseOnlineJudge;
                    SubmissaoDTO submissaoDto = new SubmissaoDTO();
                    submissaoDto.setUsuarioId(submissao.getUsuarioId());
                    submissaoDto.setProblemaId(submissao.getProblemaId());

                    try {
                        responseOnlineJudge = juizOnlinePython.realizaSubmissao(request);
                        submissaoDto.setSaida((String) responseOnlineJudge.get("saida"));
                        submissaoDto.setTempo((double) responseOnlineJudge.get("tempo"));
                        submissaoDto.setStatus((String) responseOnlineJudge.get("status"));

                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return submissaoDto;
                })
                .toList();

        return retorno;

        // TODO: Realizar um for para realizar uma requisição a cada caso de teste e guardar o seu resultado no array citado acima

        // TODO: Criar um Array de Models Submissões que serão guardados no banco de dados, usando o id do problema, o id do usuario e por fim os dados obtidos da reposta do api
    }

}