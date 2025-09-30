package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "residuo",
        indexes = {
                @Index(name = "idx_residuo_escuela", columnList = "id_escuela"),
                @Index(name = "idx_residuo_actividad", columnList = "id_actividad")
        }
)
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_residuos")
    private Long idResiduos;

    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;
}
