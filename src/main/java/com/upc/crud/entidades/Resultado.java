package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultado",
        indexes = {
                @Index(name = "idx_resultado_escuela", columnList = "id_escuela"),
                @Index(name = "idx_resultado_proyecto", columnList = "id_proyecto")
        })
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Long idResultado;

    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @Column(name = "porcentaje_reduccion", precision = 5, scale = 2)
    private BigDecimal porcentajeReduccion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;
}