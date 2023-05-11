package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.dto.EditSenhaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import com.tcc.joaomyrlla.appcode2know.exceptions.InstituicaoNotFoundException;
import com.tcc.joaomyrlla.appcode2know.exceptions.InsufficientPrivilegeException;
import com.tcc.joaomyrlla.appcode2know.exceptions.PasswordNotMatch;
import com.tcc.joaomyrlla.appcode2know.exceptions.UsuarioNotFoundException;
import com.tcc.joaomyrlla.appcode2know.model.Instituicao;
import com.tcc.joaomyrlla.appcode2know.model.Turma;
import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.InstituicaoRespository;
import com.tcc.joaomyrlla.appcode2know.repository.TurmaRepository;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        String salt = BCrypt.gensalt();
        String senhaCifrada = BCrypt.hashpw(usuario.getSenha(), salt);

        usuario.setSalt(salt);
        usuario.setSenha(senhaCifrada);


        usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO edit(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getId()).orElseThrow(UsuarioNotFoundException::new);

        BeanUtils.copyProperties(usuarioDTO, usuario, "senha", "id");
        usuario.getInstituicaoAtual().setId(usuarioDTO.getInstituicaoAtualId());

        usuarioRepository.save(usuario);
        return usuarioDTO;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void editarSenha(Long usuarioId, EditSenhaDTO editSenhaDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);

        if (!BCrypt.checkpw(editSenhaDTO.getSenhaAtual(), usuario.getSenha())) {
            throw new PasswordNotMatch();
        }

        String novoSalt = BCrypt.gensalt();
        String senhaCifrada = BCrypt.hashpw(editSenhaDTO.getNovaSenha(), novoSalt);

        usuario.setSalt(novoSalt);
        usuario.setSenha(senhaCifrada);

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void cadastrarCargo(String cargo, Long administradorId, List<Long> listaIds) {
        Usuario adm = usuarioRepository.findById(administradorId).orElseThrow(UsuarioNotFoundException::new);

        if (!adm.isEhAdm()) {
            throw new InsufficientPrivilegeException("O usuario não é um administrador");
        }

        listaIds.forEach(id -> {
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);

            if (cargo.equals("professor")) {
                usuario.setEhProfessor(true);
            } else if (cargo.equals("administrador")) {
                usuario.setEhAdm(true);
            }

            usuarioRepository.save(usuario);
        });
    }

    @Override
    @Transactional
    public void gerenciarPermissoes(Long usuarioId, Map<String, List<Long>> permissoesAlteradas) {
        permissoesAlteradas.get("novosAdms")
                .forEach(permissaoId -> {
                    Usuario usuario = usuarioRepository.findById(permissaoId).orElseThrow(UsuarioNotFoundException::new);
                    usuario.setEhAdm(true);
                    usuarioRepository.save(usuario);
                });

        permissoesAlteradas.get("antigosAdms")
                .forEach(permissaoId -> {
                    Usuario usuario = usuarioRepository.findById(permissaoId).orElseThrow(UsuarioNotFoundException::new);
                    usuario.setEhAdm(false);
                    usuarioRepository.save(usuario);
                });

        permissoesAlteradas.get("novosProfessores")
                .forEach(permissaoId -> {
                    Usuario usuario = usuarioRepository.findById(permissaoId).orElseThrow(UsuarioNotFoundException::new);
                    usuario.setEhProfessor(true);
                    usuarioRepository.save(usuario);
                });

        permissoesAlteradas.get("antigosProfessores")
                .forEach(permissaoId -> {
                    Usuario usuario = usuarioRepository.findById(permissaoId).orElseThrow(UsuarioNotFoundException::new);
                    usuario.setEhProfessor(false);
                    usuarioRepository.save(usuario);
                });
    }
}
