package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
import com.tcc.joaomyrlla.appcode2know.exceptions.TurmaNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.*;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .map(TurmaDTO::toTurma)
                .toList();
    }

    @Override
    public List<TurmaDTO> findByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        return usuario.getTurmasAluno()
                .stream()
                .map(TurmaDTO::toTurma)
                .toList();
    }

    @Override
    public TurmaDTO add(TurmaDTO turmaDTO, Long criadorId) {
        Instituicao instituicao = instituicaoRespository.findById(turmaDTO.getInstituicaoId()).orElseThrow(InstituicaoNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);

        if (!(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException(String.format("O usuario de Id %d não tem permissão para criar uma turma", criadorId));
        }

        Turma turma = Turma.toTurma(turmaDTO);
        turma.getProfessores().add(usuario);
        instituicao.setId(turmaDTO.getInstituicaoId());

        turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));

        turmaRepository.save(turma);

        turmaDTO.setId(turma.getId());

        return turmaDTO;
    }

    @Override
    public TurmaDTO edit(TurmaDTO turmaDTO, Long professorId) {
        Usuario usuario = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(turmaDTO.getId()).orElseThrow(TurmaNotFoundException::new);

        //TODO: Verificar se o id passado pertence a um professor que está na lista de professores da turma
        List<Long> listaIdProfessores = turma.getProfessores().stream().map(professor -> professor.getId()).toList();

        if (!listaIdProfessores.contains(professorId)) {
            throw new InsufficientPrivilegeException("O usuário de Id " + professorId + " não tem permissão para editar uma turma");
        }

        turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));

        turma.getInstituicao().setId(turmaDTO.getInstituicaoId());

        turmaRepository.save(turma);

        return turmaDTO;
    }

    @Override
    public void delete(Long turmaId, Long professorId) {
        Usuario usuario = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);

        //TODO: Verificar se o professorId pertence a um professor e a lista de professores da turma
        List<Long> listaIdProfessores = turma.getProfessores().stream().map(professor -> professor.getId()).toList();
        if (!listaIdProfessores.contains(professorId)) {
            throw new InsufficientPrivilegeException(String.format("O usuário de Id %d não tem permissão para deletar a turma %d", professorId, turmaId));
        }

        turmaRepository.deleteById(turmaId);
    }

    @Override
    public void addAlunoEmTurma(Long turmaId, Long alunoId, Long criadorId) {
        Usuario usuarioAluno = usuarioRepository.findById(alunoId).orElseThrow(UsuarioNotFoundException::new);
        Usuario usuarioCriador = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);

        List<Long> listaIdProfessores = turma.getProfessores().stream().map(professor -> professor.getId()).toList();
        if (!listaIdProfessores.contains(criadorId)) {
            throw new InsufficientPrivilegeException(String.format("O usuário de Id %d não tem permissão para adicionar alunos na turma %d", criadorId, turmaId));
        }

        turma.getAlunos().add(usuarioAluno);
        turmaRepository.save(turma);
    }


    @Override
    public void removerAlunoDaTurma(Long turmaId, Long alunoId, Long professorId) {
        Usuario usuarioAluno = usuarioRepository.findById(alunoId).orElseThrow(UsuarioNotFoundException::new);
        Usuario usuarioCriador = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);

        List<Long> listaIdProfessores = turma.getProfessores().stream().map(professor -> professor.getId()).toList();
        if (!listaIdProfessores.contains(professorId)) {
            throw new InsufficientPrivilegeException(String.format("O usuário de Id %d não tem permissão para adicionar alunos na turma %d", professorId, turmaId));
        }

        turma.getAlunos().remove(usuarioAluno);
        turmaRepository.save(turma);
    }

    @Override
    public void addProfessorEmTurma(Long turmaId, Long professorId, Long professorAdicionadoId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);
        Usuario professor = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Usuario professorAdicionado = usuarioRepository.findById(professorAdicionadoId).orElseThrow(UsuarioNotFoundException::new);

        if (!professor.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O usuario de Id %d não tem permissão para adicionar professores na turma", professorId));
        }

        if (!professorAdicionado.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O id %d não pertence a um professor", professorAdicionadoId));
        }

        turma.getProfessores().add(professor);

        turmaRepository.save(turma);
    }

    @Override
    public void removerProfessorDaTurma(Long turmaId, Long professorId, Long professorAdicionadoId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(TurmaNotFoundException::new);
        Usuario professor = usuarioRepository.findById(professorId).orElseThrow(UsuarioNotFoundException::new);
        Usuario professorAdicionado = usuarioRepository.findById(professorAdicionadoId).orElseThrow(UsuarioNotFoundException::new);

        if (!professor.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O usuario de id %d não ter permissão para remover professores da turma"), professorId);
        }

        if (!professorAdicionado.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O usuário de id %d não é um professor", professorAdicionadoId));
        }

        turma.getProfessores().remove(professor);
        turmaRepository.save(turma);
    }
}
