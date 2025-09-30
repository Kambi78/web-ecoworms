package com.upc.crud.repositorios;

import com.upc.crud.entidades.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultadoRepositorio extends JpaRepository<Resultado, Long> {
    List<Resultado> findByProyecto_IdProyecto(Long idProyecto);
    List<Resultado> findByEscuela_IdEscuela(Long escuelaId);
}