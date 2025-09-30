package com.upc.crud.repositorios;

import com.upc.crud.entidades.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ActividadRepositorio extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a WHERE a.usuario.idUsuario = :usuarioId AND a.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Actividad> findActividadesByUsuarioAndFecha(@Param("usuarioId") Long usuarioId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    List<Actividad> findByUsuario_IdUsuario(Long usuarioId);
    List<Actividad> findByPrograma_IdPrograma(Long programaId);

    @Query("""
       SELECT key(m), SUM(value(m)) FROM Actividad a JOIN a.materiales m
       WHERE a.programa.idPrograma = :programaId
       GROUP BY key(m)
       """)
    List<Object[]> countMaterialesPorPrograma(@Param("programaId") Long programaId);
    @Query("""
        SELECT a FROM Actividad a
        WHERE a.programa.idPrograma = :programaId AND (:equipo IS NULL OR a.equipo = :equipo)
          AND (:ini IS NULL OR a.fecha >= :ini) AND (:fin IS NULL OR a.fecha <= :fin)
        """)
    List<Actividad> findByProgramaAndFilters(@Param("programaId") Long programaId, @Param("equipo") String equipo,
                                             @Param("ini") LocalDate ini, @Param("fin") LocalDate fin);
}
