package com.tcc.joaomyrlla.appcode2know.repository;

import com.tcc.joaomyrlla.appcode2know.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

}
