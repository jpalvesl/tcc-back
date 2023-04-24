package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Autowired
    TurmaRepository turmaRepository;

    @Override
    public List<UsuarioDTO> findByInstituicao(Long instituicaoId) {
        Instituicao instituicao = instituicaoRespository.findById(instituicaoId).orElseThrow(InstituicaoNotFoundException::new);

        List<Usuario> usuarios = instituicao.getAlunos();

        return usuarios.stream()
                .map(UsuarioDTO::toUsuarioDTO)
                .toList();
    }

    @Override
    public List<UsuarioDTO> findAlunoByInstituicao(Long instituicaoId) {
        Instituicao instituicao = instituicaoRespository.findById(instituicaoId).orElseThrow(InstituicaoNotFoundException::new);

        List<Usuario> usuarios = instituicao.getAlunos();

        return usuarios.stream()
                .filter(usuario -> !usuario.isEhProfessor())
                .map(UsuarioDTO::toUsuarioDTO)
                .toList();
    }

    @Override
    public List<UsuarioDTO> findProfessorByInstituicao(Long instituicaoId) {
        Instituicao instituicao = instituicaoRespository.findById(instituicaoId).orElseThrow(InstituicaoNotFoundException::new);

        List<Usuario> usuarios = instituicao.getAlunos();

        return usuarios.stream()
                .filter(Usuario::isEhProfessor)
                .map(UsuarioDTO::toUsuarioDTO)
                .toList();
    }

    @Override
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);

        return UsuarioDTO.toUsuarioDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> findByTurma(Long turmaId) {
        List<Turma> result = turmaRepository.findAll()
                .stream()
                .filter(turma -> turma.getId().equals(turmaId))
                .toList();

        if (result.isEmpty()) {
            throw new RuntimeException("Não existe turma com o id " + turmaId);
        }

        List<Usuario> alunos = result.get(0).getAlunos();

        return alunos.stream()
                .map(UsuarioDTO::toUsuarioDTO)
                .toList();
    }

    @Override
    public UsuarioDTO add(UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.toUsuario(usuarioDTO);

        usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO edit(UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.toUsuario(usuarioDTO);

        usuarioRepository.save(usuario);
        return usuarioDTO;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
