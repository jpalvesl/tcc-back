package com.tcc.joaomyrlla.appcode2know.serviceImpl;

import com.tcc.joaomyrlla.appcode2know.model.Usuario;
import com.tcc.joaomyrlla.appcode2know.repository.UsuarioRepository;
import com.tcc.joaomyrlla.appcode2know.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findByInstituicao() {
        return null;
    }

    @Override
    public Usuario findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) return null;
        return usuario.get();
    }

    @Override
    public List<Usuario> findByTurma(Long turmaId) {
        // TODO: implementar turma antes de implementar essa parte
        return null;
    }

    @Override
    public Usuario add(Usuario usuario) {
        // TODO: Implementar DTO para ter a instituicao_id e com ele fazer a busca e setar o model para salvar corretamente
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario edit(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
