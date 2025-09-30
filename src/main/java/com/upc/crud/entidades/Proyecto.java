
package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyecto",
        indexes = {
                @Index(name = "idx_proyecto_escuela", columnList = "id_escuela"),
                @Index(name = "idx_proyecto_programa", columnList = "id_programa")
        })
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "fase", length = 50, nullable = false)
    private String fase;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultado> resultados;
}
