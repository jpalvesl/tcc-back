package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Instituicao> result = instituicaoRespository.findById(instituicaoId);

        if (result.isEmpty()) {
            throw new RuntimeException("Não existem instituição com o id " + instituicaoId);
        }

        List<Usuario> usuarios = result.get().getAlunos();

        return usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    BeanUtils.copyProperties(usuario, usuarioDTO);
                    usuarioDTO.setInstituicaoAtualId(usuario.getInstituicaoAtual().getId());

                    return usuarioDTO;
                })
                .toList();
    }

    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> result = usuarioRepository.findById(id);

        if (result.isEmpty()) return null;
        Usuario usuario = result.get();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        BeanUtils.copyProperties(usuario, usuarioDTO);
        usuarioDTO.setInstituicaoAtualId(usuario.getInstituicaoAtual().getId());
        
        return usuarioDTO;
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
                        .map(aluno -> {
                            UsuarioDTO alunoDto = new UsuarioDTO();
                            BeanUtils.copyProperties(aluno, alunoDto);
                            alunoDto.setInstituicaoAtualId(aluno.getInstituicaoAtual().getId());

                            return alunoDto;
                        })
                        .toList();
    }

    @Override
    public UsuarioDTO add(UsuarioDTO usuario) {
        Usuario novoUsusario = new Usuario();
        BeanUtils.copyProperties(usuario, novoUsusario);

        Instituicao instituicao = new Instituicao();
        instituicao.setId(usuario.getInstituicaoAtualId());
        novoUsusario.setInstituicaoAtual(instituicao);

        usuarioRepository.save(novoUsusario);
        usuario.setId(novoUsusario.getId());

        return usuario;
    }

    @Override
    public UsuarioDTO edit(UsuarioDTO usuario) {
        Usuario ususarioEditado = new Usuario();
        BeanUtils.copyProperties(usuario, ususarioEditado);

        Instituicao instituicao = new Instituicao();
        instituicao.setId(usuario.getInstituicaoAtualId());
        ususarioEditado.setInstituicaoAtual(instituicao);

        usuarioRepository.save(ususarioEditado);
        return usuario;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
