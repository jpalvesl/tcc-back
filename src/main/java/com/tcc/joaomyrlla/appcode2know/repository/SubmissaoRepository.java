package com.tcc.joaomyrlla.appcode2know.repository;

import com.tcc.joaomyrlla.appcode2know.model.Submissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
}
