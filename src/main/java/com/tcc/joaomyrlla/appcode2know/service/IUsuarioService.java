package com.tcc.joaomyrlla.appcode2know.service;

import com.tcc.joaomyrlla.appcode2know.dto.EditSenhaDTO;
import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {
    List<UsuarioDTO> findByInstituicao(Long instituicaoId);

    List<UsuarioDTO> findAlunoByInstituicao(Long instituicaoId);

    List<UsuarioDTO> findProfessorByInstituicao(Long instituicaoId);

    UsuarioDTO findById(Long id);

    List<UsuarioDTO> findByTurma(Long turmaId);

    UsuarioDTO add(UsuarioDTO usuarioDTO);

    UsuarioDTO edit(UsuarioDTO usuarioDTO);

    void delete(Long id);

    void editarSenha(Long usuarioId, EditSenhaDTO editSenhaDTO);

    void cadastrarCargo(String cargo, Long administradorId, List<Long> listaIds);

    void gerenciarPermissoes(Long usuarioId, Map<String, List<Long>> permissoesAlteradas);
}
