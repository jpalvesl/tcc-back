package com.tcc.joaomyrlla.appcode2know.model;

import com.tcc.joaomyrlla.appcode2know.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Table(name = "USUARIO")
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 30)
    private String usuario;

    @Column(nullable = false, length = 20)
    private String senha;

    private String imagemUrl;

    @Column(nullable = false)
    private boolean ehAdm;

    @Column(nullable = false)
    private boolean ehProfessor;

    @ManyToOne
    @JoinColumn(name = "INSTITUICAO_ATUAL_ID")
    private Instituicao instituicaoAtual = new Instituicao();

    @ManyToMany(mappedBy = "professores")
    @Lazy
    private List<Turma> turmasProfessor = new ArrayList<>();

    @ManyToMany(mappedBy = "alunos")
    @Lazy
    private List<Turma> turmasAluno = new ArrayList<>();

    public static Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        usuario.getInstituicaoAtual().setId(usuarioDTO.getInstituicaoAtualId());

        return usuario;
    }

    public static Usuario toUsuario(UsuarioDTO usuarioDTO, Usuario usuario) {
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.getInstituicaoAtual().setId(usuarioDTO.getInstituicaoAtualId());

        return usuario;
    }
}