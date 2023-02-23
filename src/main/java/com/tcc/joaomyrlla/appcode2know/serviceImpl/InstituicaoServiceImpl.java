package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoServiceImpl implements IInstituicaoService {
    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Override
    public List<Instituicao> findAll() {
        return instituicaoRespository.findAll();
    }

    @Override
    public Instituicao findById(Long id) {
        Optional<Instituicao> instituicao = instituicaoRespository.findById(id);
        if (instituicao.isEmpty()) return null;
        return instituicaoRespository.findById(id).get();
    }

    @Override
    public Instituicao add(Instituicao instituicao) {
        return instituicaoRespository.save(instituicao);
    }

    @Override
    public void delete(Long id, Long usuarioId) {
        // Todo: Fazer verificacao de usuario para verificar se ele possui privilegios de professor

        if (!instituicaoRespository.existsById(id)) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        // TODO: antes de deletar a instituicao, passar para null todos os registros de alunos que possuem aquela instituicao como atual
        instituicaoRespository.deleteById(id);
    }

    @Override
    public Instituicao edit(Instituicao instituicao, Long usuarioId) {
        // Todo: Fazer verificacao de usuario para verificar se ele possui privilegios de professor

        if (!instituicaoRespository.existsById(instituicao.getId())) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        return instituicao;
    }
}
