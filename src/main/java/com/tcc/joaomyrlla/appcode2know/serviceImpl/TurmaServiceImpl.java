package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.model.*;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaServiceImpl implements ITurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    InstituicaoRespository instituicaoRespository;

    @Override
    public List<TurmaDTO> findByInstituicao(Long instituicaoId) {
        return turmaRepository.findAll()
                .stream()
                .filter(turma -> turma.getInstituicao().getId().equals(instituicaoId))
                .map(turma -> {
                    TurmaDTO turmaDTO = new TurmaDTO();
                    BeanUtils.copyProperties(turma, turmaDTO);
                    turmaDTO.setInstituicaoId(turma.getInstituicao().getId());

                    turmaDTO.setTitulo(String.join(" - ",turma.getNomeTurma(), turma.getSemestre()));

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
                    turmaDTO.setTitulo(String.join(" - ",turma.getNomeTurma(), turma.getSemestre()));
                    turmaDTO.setInstituicaoId(turma.getInstituicao().getId());

                    return turmaDTO;
                })
                .toList();
    }

    @Override
    public TurmaDTO add(TurmaDTO turma, Long criadorId) {

        Optional<Instituicao> instituicaoOptional = instituicaoRespository.findById(turma.getInstituicaoId());

        if(instituicaoOptional.isEmpty()){
            throw new RuntimeException("A instituição não existe");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(criadorId);
        if(usuarioOptional.isEmpty()){
            throw new RuntimeException("O usuário de Id " + criadorId + " não existe");
        }

        if(!(usuarioOptional.get().isEhProfessor())){
            throw new RuntimeException("O usuário de Id " + criadorId + " não tem permissão para criar uma turma");
        }


        Turma novaTurma = new Turma();
        BeanUtils.copyProperties(turma, novaTurma);

        turma.setTitulo(String.join(" - ",novaTurma.getNomeTurma(), novaTurma.getSemestre()));


        Instituicao instituicao = new Instituicao();
        instituicao.setId(turma.getInstituicaoId());
        novaTurma.setInstituicao(instituicao);

        turmaRepository.save(novaTurma);

        turma.setId(novaTurma.getId());

        return turma;
    }

    @Override
    public TurmaDTO edit(TurmaDTO turma, Long professorId) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(professorId);

        Optional<Turma> turmaOptional = turmaRepository.findById(turma.getId());
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id " + professorId + " não existe");
        }
        if (!turmaRepository.existsById(turma.getId())) {
            throw new RuntimeException(String.format("Turma com id %d não existe", turma.getId()));
        }

        //TODO: Verificar se o id passado pertence a um professor que está na lista de professores da turma

        turmaOptional.get().getProfessores().forEach(professor -> {
            if(!professor.getId().equals(professorId)){
                throw new RuntimeException("O usuário de Id " + professorId + " não tem permissão para editar uma turma");
            }
        });

        Turma turmaEditada = new Turma();
        BeanUtils.copyProperties(turma, turmaEditada);
        turma.setTitulo(String.join(" - ",turmaEditada.getNomeTurma(), turmaEditada.getSemestre()));


        Instituicao instituicao = new Instituicao();
        instituicao.setId(turma.getInstituicaoId());
        turmaEditada.setInstituicao(instituicao);

        turmaRepository.save(turmaEditada);

        return turma;
    }

    @Override
    public void delete(Long turmaId, Long professorId) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(professorId);

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("O usuário de Id " + professorId + " não existe");
        }

        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);

        if(turmaOptional.isEmpty()){
            throw new RuntimeException("A turma de Id " + turmaId + " não existe");
        }

        //TODO: Verificar se o professorId pertence a um professor e a lista de professores da turma
        turmaOptional.get().getProfessores().forEach(professor -> {
            if(!professor.getId().equals(professorId)){
                throw new RuntimeException("O usuário de Id " + professorId + " não tem permissão para deletar uma turma");
            }
        });

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
        turmaOptional.get().getProfessores().forEach(professor -> {
            if(!professor.getId().equals(criadorId)){
                throw new RuntimeException("O usuário de Id " + criadorId + " não tem permissão para adicionar alunos na turma");
            }
        });

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

        turmaOptional.get().getProfessores().forEach(professor -> {
            if(!professor.getId().equals(professorId)){
                throw new RuntimeException("O usuário de Id " + professorId + " não tem permissão para remover alunos da turma");
            }
        });

        turmaOptional.get().getAlunos().remove(usuarioAluno.get());
        turmaRepository.save(turmaOptional.get());

    }

    @Override
    public void addProfessorEmTurma(Long turmaId, Long professorId, Long professorAdicionadoId) {
        Optional<Turma> optionalTurma = turmaRepository.findById(turmaId);
        Optional<Usuario> optionalProfessor = usuarioRepository.findById(professorId);
        Optional<Usuario> optionalProfessorAdicionado = usuarioRepository.findById(professorAdicionadoId);

        if (optionalTurma.isEmpty()) {
            throw new RuntimeException("Turma não encontrada");
        }

        if (optionalProfessor.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }
        if (optionalProfessorAdicionado.isEmpty()) {
            throw new RuntimeException("Professor de Id" + professorAdicionadoId + " não existe");
        }

        if (!optionalProfessor.get().isEhProfessor()) {
            throw new RuntimeException("O usuário de Id " + professorId + " não tem permissão para adicionar professores");
        }

        if (!optionalProfessorAdicionado.get().isEhProfessor()) {
            throw new RuntimeException("O Id " + professorAdicionadoId + " não pertence a um professor");
        }

        Turma turma = optionalTurma.get();
        Usuario professor = optionalProfessor.get();

        turma.getProfessores().add(professor);

        turmaRepository.save(turma);
    }

    @Override
    public void removerProfessorDaTurma(Long turmaId, Long professorId, Long professorAdicionadoId) {
        Optional<Turma> optionalTurma = turmaRepository.findById(turmaId);
        Optional<Usuario> optionalProfessor = usuarioRepository.findById(professorId);
        Optional<Usuario> optionalProfessorAdicionado = usuarioRepository.findById(professorAdicionadoId);

        if (optionalTurma.isEmpty()) {
            throw new RuntimeException("Turma não encontrada");
        }

        if (optionalProfessor.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }
        if (optionalProfessorAdicionado.isEmpty()) {
            throw new RuntimeException("Professor de Id" + professorAdicionadoId + " não existe");
        }

        if (!optionalProfessor.get().isEhProfessor()) {
            throw new RuntimeException("O usuário de Id " + professorId + " não tem permissão para remover professores");
        }

        if (!optionalProfessorAdicionado.get().isEhProfessor()) {
            throw new RuntimeException("O Id " + professorAdicionadoId + " não pertence a um professor");
        }

        Turma turma = optionalTurma.get();
        Usuario professor = optionalProfessor.get();

        turma.getProfessores().remove(professor);
        turmaRepository.save(turma);
    }
}
