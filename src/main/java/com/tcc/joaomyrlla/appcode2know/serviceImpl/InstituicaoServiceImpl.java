package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class InstituicaoServiceImpl implements IInstituicaoService {
    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Override
    public List<InstituicaoDTO> findAll() {
        List<Instituicao> result = instituicaoRespository.findAll();

        List<InstituicaoDTO> instituicaoDTOs = new ArrayList<>();
        result.forEach(instituicao -> {
            InstituicaoDTO instituicaoDTO = new InstituicaoDTO();
            BeanUtils.copyProperties(instituicao, instituicaoDTO);

            instituicaoDTOs.add(instituicaoDTO);
        });

        return instituicaoDTOs;
    }

    @Override
    public InstituicaoDTO findById(Long id) {
        Optional<Instituicao> instituicao = instituicaoRespository.findById(id);
        if (instituicao.isEmpty()) return null;

        InstituicaoDTO instituicaoDTO = new InstituicaoDTO();

        BeanUtils.copyProperties(instituicao.get(), instituicaoDTO);
        return instituicaoDTO;
    }

    @Override
    public InstituicaoDTO add(InstituicaoDTO instituicao) {
        Instituicao novaInstituicao = new Instituicao();
        BeanUtils.copyProperties(instituicao, novaInstituicao);
        
        instituicaoRespository.save(novaInstituicao);

        instituicao.setId(novaInstituicao.getId());

        return instituicao;
    }

    @Override
    public void delete(Long id, Long usuarioId) {
        // Todo: Fazer verificacao de usuario para verificar se ele possui privilegios de adm

        if (!instituicaoRespository.existsById(id)) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        // TODO: antes de deletar a instituicao, passar para null todos os registros de alunos que possuem aquela instituicao como atual
        instituicaoRespository.deleteById(id);
    }

    @Override
    public InstituicaoDTO edit(InstituicaoDTO instituicao, Long usuarioId) {
        // Todo: Fazer verificacao de usuario para verificar se ele possui privilegios de adm

        if (!instituicaoRespository.existsById(instituicao.getId())) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        Instituicao instituicaoEditada = new Instituicao();
        BeanUtils.copyProperties(instituicao, instituicaoEditada);

        instituicaoRespository.save(instituicaoEditada);

        return instituicao;
    }
}
