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
                .map(turma -> {
                    TurmaDTO turmaDTO = new TurmaDTO();
                    BeanUtils.copyProperties(turma, turmaDTO);
                    turmaDTO.setInstituicaoId(turma.getInstituicao().getId());

                    turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));

                    return turmaDTO;
                })
                .toList();
    }

    @Override
    public List<TurmaDTO> findByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        return usuario.getTurmasAluno()
                .stream()
                .map(turma -> {
                    TurmaDTO turmaDTO = new TurmaDTO();
                    BeanUtils.copyProperties(turma, turmaDTO);
                    turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));
                    turmaDTO.setInstituicaoId(turma.getInstituicao().getId());

                    return turmaDTO;
                })
                .toList();
    }

    @Override
    public TurmaDTO add(TurmaDTO turma, Long criadorId) {
        Instituicao instituicao = instituicaoRespository.findById(turma.getInstituicaoId()).orElseThrow(InstituicaoNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);

        if (!(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException(String.format("O usuario de Id %d não tem permissão para criar uma turma", criadorId));
        }


        List<Usuario> professoresTurma = new ArrayList<>();
        professoresTurma.add(usuario);

        Turma novaTurma = new Turma();
        BeanUtils.copyProperties(turma, novaTurma);

        turma.setTitulo(String.join(" - ", novaTurma.getNomeTurma(), novaTurma.getSemestre()));

        instituicao.setId(turma.getInstituicaoId());
        novaTurma.setInstituicao(instituicao);
        novaTurma.setProfessores(professoresTurma);

        turmaRepository.save(novaTurma);

        turma.setId(novaTurma.getId());

        return turma;
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

        Turma turmaEditada = new Turma();
        BeanUtils.copyProperties(turma, turmaEditada);
        turmaDTO.setTitulo(String.join(" - ", turmaEditada.getNomeTurma(), turmaEditada.getSemestre()));


        Instituicao instituicao = new Instituicao();
        instituicao.setId(turmaDTO.getInstituicaoId());
        turmaEditada.setInstituicao(instituicao);
        turmaEditada.getProfessores().add(usuario);

        turmaRepository.save(turmaEditada);

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
