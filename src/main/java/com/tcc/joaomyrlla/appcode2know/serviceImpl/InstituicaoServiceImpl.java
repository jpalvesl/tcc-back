package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.dto.InstituicaoDTO;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IInstituicaoService;
import jakarta.transaction.Transactional;
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

    @Autowired
    UsuarioRepository usuarioRepository;

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
    @Transactional
    public void delete(Long id, Long usuarioId) {
        
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if (!(usuario.get().isEhAdm())) {
            throw new RuntimeException("O usuário não tem permissão para deletar a instituição");
        }

        if (!instituicaoRespository.existsById(id)) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        List<Usuario> result = usuarioRepository.findAll();
        result.forEach(usuario1 -> {
            if(usuario.get().getInstituicaoAtual().getId().equals(id)){
                usuario.get().setInstituicaoAtual(null);
                Usuario usuarioMod = new Usuario();
                BeanUtils.copyProperties(usuario, usuarioMod);
                usuarioRepository.save(usuarioMod);
            }
        });


        instituicaoRespository.deleteById(id);

    }

    @Override
    public InstituicaoDTO edit(InstituicaoDTO instituicao, Long usuarioId) {


        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if (!(usuario.get().isEhAdm())) {
            throw new RuntimeException("O usuário não tem permissão para editar a instituição");
        }


        if (!instituicaoRespository.existsById(instituicao.getId())) {
            throw new RuntimeException("Instituicao com o ID informado não existe");
        }

        Instituicao instituicaoEditada = new Instituicao();
        BeanUtils.copyProperties(instituicao, instituicaoEditada);

        instituicaoRespository.save(instituicaoEditada);

        return instituicao;
    }
}
