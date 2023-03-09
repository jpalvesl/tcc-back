package com.tcc.joaomyrlla.appcode2know.model.multivalorado;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "TOPICO")
@Data
@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topico;

    private String problemaId;
}
