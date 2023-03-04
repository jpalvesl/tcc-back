package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.model.*;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaServiceImpl implements ITurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<TurmaDTO> findByInstituicao(Long instituicaoId) {
        return turmaRepository.findAll()
                .stream()
                .filter(turma -> turma.getInstituicao().getId().equals(instituicaoId))
                .map(turma -> {
                    TurmaDTO turmaDTO = new TurmaDTO();
                    BeanUtils.copyProperties(turma, turmaDTO);
                    turmaDTO.setInstituicaoId(turma.getInstituicao().getId());

                    return turmaDTO;
                })
                .toList();
    }

    @Override
    public List<TurmaDTO> findByUsuario(Long usuarioId) {
        Optional<Usuario> result = usuarioRepository.findById(usuarioId);

        if (result.isEmpty()) {
            throw new RuntimeException("Usuário não existente");
        }

        return result.get().getTurmasAluno()
                .stream()
                .map(turma -> {
                    TurmaDTO turmaDTO = new TurmaDTO();
                    BeanUtils.copyProperties(turma, turmaDTO);

                    return turmaDTO;
                })
                .toList();
    }

    @Override
    public TurmaDTO add(TurmaDTO turma) {
        Turma novaTurma = new Turma();
        BeanUtils.copyProperties(turma, novaTurma);


        Instituicao instituicao = new Instituicao();
        instituicao.setId(turma.getInstituicaoId());
        novaTurma.setInstituicao(instituicao);

        turmaRepository.save(novaTurma);

        turma.setId(novaTurma.getId());

        return turma;
    }

    @Override
    public TurmaDTO edit(TurmaDTO turma, Long professorId) {
        Turma turmaEditada = new Turma();
        BeanUtils.copyProperties(turma, turmaEditada);


        Instituicao instituicao = new Instituicao();
        instituicao.setId(turma.getInstituicaoId());
        turmaEditada.setInstituicao(instituicao);

        turmaRepository.save(turmaEditada);

        return turma;
    }

    @Override
    public void delete(Long turmaId) {
        turmaRepository.deleteById(turmaId);
    }

    @Override
    public void addAlunoEmTurma (Long turmaId, Long alunoId, Long criadorId){
        Optional<Usuario> usuarioAluno = usuarioRepository.findById(alunoId);
        Optional<Usuario> usuarioCriador = usuarioRepository.findById(criadorId);
        if (usuarioAluno.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if (usuarioCriador.isEmpty()) {
            throw new RuntimeException("O usuário criador não existe");
        }

        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if(turmaOptional.isEmpty()){
            throw new RuntimeException("Turma com o ID informado não existe");
        }

        //TODO: Verificar se o id passado pertence a um dos professores da turma

        turmaOptional.get().getAlunos().add(usuarioAluno.get());
        turmaRepository.save(turmaOptional.get());
    }


    @Override
    public void removerAlunoDaTurma (Long turmaId, Long alunoId, Long professorId){

        Optional<Usuario> usuarioAluno = usuarioRepository.findById(alunoId);
        Optional<Usuario> usuarioCriador = usuarioRepository.findById(professorId);
        if (usuarioAluno.isEmpty()) {
            throw new RuntimeException("O usuário não existe");
        }

        if (usuarioCriador.isEmpty()) {
            throw new RuntimeException("O usuário criador não existe");
        }

        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if(turmaOptional.isEmpty()){
            throw new RuntimeException("Turma com o ID informado não existe");
        }

        //TODO: Verificar se o id passado pertence a um dos professores da turma

        turmaOptional.get().getAlunos().remove(usuarioAluno.get());
        turmaRepository.save(turmaOptional.get());

    }
}
