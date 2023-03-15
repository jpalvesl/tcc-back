package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.tcc.joaomyrlla.appcode2know.dto.CasoDeTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.RespostaDeCasoTesteDTO;
import com.tcc.joaomyrlla.appcode2know.dto.SubmissaoDTO;
import com.tcc.joaomyrlla.appcode2know.model.Problema;
import com.tcc.joaomyrlla.appcode2know.model.RespostaCasoTeste;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.RespostaCasoDeTesteRepository;
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
    ICasoDeTesteService casoDeTesteService;

    @Autowired
    RespostaCasoDeTesteRepository respostaCasoDeTesteRepository;

    final
    SubmissaoRepository submissaoRepository;


    JuizOnlinePython juizOnlinePython = new JuizOnlinePython();

    public SubmissaoServiceImpl(SubmissaoRepository submissaoRepository) {
        this.submissaoRepository = submissaoRepository;
    }


    @Override
    public List<SubmissaoDTO> findAll() {
        List<Submissao> submissoes = submissaoRepository.findAll();


        return submissoes.stream()
                .map(submissao -> {
                    SubmissaoDTO submissaoDto = new SubmissaoDTO();
                    BeanUtils.copyProperties(submissao, submissaoDto);
                    submissaoDto.setProblemaId(submissao.getProblema().getId());
                    submissaoDto.setUsuarioId(submissao.getUsuario().getId());


                    return submissaoDto;
                })
                .toList();
    }

    @Override
    public List<SubmissaoDTO> findByProblemaId(Long problemaId) {
        List<SubmissaoDTO> listaSubmissoesPorProblema = submissaoRepository.findAll()
                .stream()
                .filter(submissao -> submissao.getProblema().getId().equals(problemaId))
                .map(submissao -> {
                    SubmissaoDTO submissaoDto = new SubmissaoDTO();
                    BeanUtils.copyProperties(submissao, submissaoDto);
                    submissaoDto.setProblemaId(submissao.getProblema().getId());
                    submissaoDto.setUsuarioId(submissao.getUsuario().getId());


                    return submissaoDto;
                })
                .toList();
        return listaSubmissoesPorProblema;
    }

    @Override
    public List<RespostaDeCasoTesteDTO> realizaSubmissao(SubmissaoDTO submissao) {
        // TODO: Buscar casos de teste relacionados ao problema
        List<CasoDeTesteDTO> casosDeTeste = casoDeTesteService.findByProblema(submissao.getProblemaId());

        Submissao novaSubmissao = new Submissao();
        Problema problema = new Problema();
        problema.setId(submissao.getProblemaId());

        Usuario usuario = new Usuario();
        usuario.setId(submissao.getUsuarioId());

        novaSubmissao.setCodigoResposta(submissao.getCodigoResposta());
        novaSubmissao.setProblema(problema);
        novaSubmissao.setUsuario(usuario);

        submissaoRepository.save(novaSubmissao);

        // TODO:  criar uma lista que guardara todas as respostas obtidas pela api em python
        List<RespostaDeCasoTesteDTO> retorno = casosDeTeste.stream()
                .map(casoTeste -> {
                    HashMap<String, Object> request = new HashMap<>();
                    request.put("codigoResposta", submissao.getCodigoResposta());
                    request.put("entradas", casoTeste.getEntrada());

                    HashMap<String, Object> responseOnlineJudge;
                    RespostaDeCasoTesteDTO respostaDeCasoTesteDto = new RespostaDeCasoTesteDTO();
                    respostaDeCasoTesteDto.setSubmissaoId(novaSubmissao.getId());
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
            respostaCasoTeste.setSubmissao(novaSubmissao);

            respostaCasoDeTesteRepository.save(respostaCasoTeste);
        });

        return retorno;
    }

}