package com.upc.crud.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario",
        uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
        indexes = {
                @Index(name = "idx_usuario_email", columnList = "email"),
                @Index(name = "idx_usuario_escuela", columnList = "id_escuela")
        })
public class Usuario {
    public enum Rol { ADMIN, COORDINADOR, ESTUDIANTE, INVESTIGADOR }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20, nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Entrevista> entrevistas;
}
