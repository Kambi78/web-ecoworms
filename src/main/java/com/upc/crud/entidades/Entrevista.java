package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entrevista",
        indexes = { @Index(name = "idx_entrevista_usuario", columnList = "id_usuario") })
public class Entrevista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrevista")
    private Long idEntrevista;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "preguntas", length = 100)
    private String preguntas;

    @Column(name = "respuestas", length = 100)
    private String respuestas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
