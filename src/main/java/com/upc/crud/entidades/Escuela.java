package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "escuela",
        indexes = {
                @Index(name = "idx_escuela_nombre", columnList = "nombre")
        })
public class Escuela {

    public enum TipoEscuela { PUBLICA, PRIVADA}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escuela")
    private Long idEscuela;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 30, nullable = false)
    private TipoEscuela tipo;

    @OneToMany(mappedBy = "escuela")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "escuela")
    private List<Programa> programas;

    @OneToMany(mappedBy = "escuela")
    private List<Proyecto> proyectos;

    @OneToMany(mappedBy = "escuela")
    private List<Residuo> residuos;

    @OneToMany(mappedBy = "escuela")
    private List<Resultado> resultados;
}