package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.TurmaDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
import com.tcc.joaomyrlla.appcode2know.exceptions.TurmaNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.ITurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TurmaServiceImpl implements ITurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    InstituicaoRespository instituicaoRespository;


    public TurmaDTO findById(Long id) {
        Turma turma = turmaRepository.findById(id).orElseThrow(TurmaNotFoundException::new);
        return TurmaDTO.toTurma(turma);
    }

    @Override
    public List<TurmaDTO> findByInstituicao(Long instituicaoId) {
        return turmaRepository.findAll()
                .stream()
                .filter(turma -> turma.getInstituicao() != null && turma.getInstituicao().getId().equals(instituicaoId))
                .map(TurmaDTO::toTurma)
                .toList();
    }

    @Override
    public Map<String, List<TurmaDTO>> findByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        Map<String, List<TurmaDTO>> turmas = new HashMap<>();
        turmas.put("aluno", findByAluno(usuario));
        turmas.put("professor", findByProfessor(usuario));
        turmas.put("monitor", findByMonitor(usuario));

        return turmas;
    }

    private List<TurmaDTO> findByAluno(Usuario usuario) {
        return usuario.getTurmasAluno()
                .stream()
                .map(TurmaDTO::toTurma)
                .toList();
    }

    private List<TurmaDTO> findByProfessor(Usuario usuario) {
        return usuario.getTurmasProfessor()
                .stream()
                .map(TurmaDTO::toTurma)
                .toList();
    }

    private List<TurmaDTO> findByMonitor(Usuario usuario) {
        return turmaRepository.findAll().stream()
                .filter(turma -> {
                    List<Long> monitoresId = turma.getMonitores().stream().map(Usuario::getId).toList();

                    return monitoresId.contains(usuario.getId());
                }).map(TurmaDTO::toTurma).toList();
    }

    @Override
    public TurmaDTO add(TurmaDTO turmaDTO, Long criadorId) {
        Instituicao instituicao = instituicaoRespository.findById(turmaDTO.getInstituicaoId()).orElseThrow(InstituicaoNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(criadorId).orElseThrow(UsuarioNotFoundException::new);

        if (!(usuario.isEhProfessor())) {
            throw new InsufficientPrivilegeException(String.format("O usuario de Id %d não tem permissão para criar uma turma", criadorId));
        }

        Turma turma = Turma.toTurma(turmaDTO);
        turma.setInstituicao(instituicao);
        instituicao.setId(turmaDTO.getInstituicaoId());
        turma.getProfessores().add(usuario);

        HashMap<String, Object> mapProfessor = new HashMap<>();
        mapProfessor.put("id", usuario.getId());
        mapProfessor.put("nome", usuario.getNome());
        turmaDTO.getProfessores().add(mapProfessor);

        turmaDTO.setTitulo(String.join(" - ", turma.getNomeTurma(), turma.getSemestre()));

        UUID chave = UUID.randomUUID();

        turma.setChave(chave.toString());
        turmaDTO.setChave(chave.toString());

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
        Turma turmaEditada = Turma.toTurma(turmaDTO);

        turmaEditada.setAlunos(turma.getAlunos());
        turmaEditada.setTarefas(turma.getTarefas());
        turmaEditada.setInstituicao(turma.getInstituicao());
        turmaEditada.setChave(turma.getChave());
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
            throw new InsufficientPrivilegeException("O usuario de id %d não ter permissão para remover professores da turma", professorId);
        }

        if (!professorAdicionado.isEhProfessor()) {
            throw new InsufficientPrivilegeException(String.format("O usuário de id %d não é um professor", professorAdicionadoId));
        }

        turma.getProfessores().remove(professor);
        turmaRepository.save(turma);
    }

    @Override
    public void entrarEmTurma(Long usuarioId, String chave) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        List<Turma> turmas = turmaRepository.findAll().stream().filter(turma -> turma.getChave().equals(chave)).toList();

        if (turmas.size() == 0) {
            throw new RuntimeException("Não existe turma com essa chave passada");
        }

        Turma turma = turmas.get(0);
        turma.getAlunos().add(usuario);
        turmaRepository.save(turma);
    }
}
