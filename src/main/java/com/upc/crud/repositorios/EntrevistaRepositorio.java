package com.upc.crud.repositorios;

import com.upc.crud.entidades.Entrevista;
import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.time.LocalDate;

public interface EntrevistaRepositorio extends JpaRepository<Entrevista, Long> {
    List<Entrevista> findByUsuarioIdUsuario(Long usuarioId);
    List<Entrevista> findByFechaBetween(LocalDate desde, LocalDate hasta);
    @EntityGraph(attributePaths = {"usuario", "usuario.escuela"})
    List<Entrevista> findAll();
    @EntityGraph(attributePaths = {"usuario", "usuario.escuela"})
    List<Entrevista> findByUsuarioIdUsuarioOrderByFechaDesc(Long usuarioId);
    @EntityGraph(attributePaths = {"usuario", "usuario.escuela"})
    List<Entrevista> findByFechaBetweenOrderByFechaDesc(LocalDate desde, LocalDate hasta);
}
