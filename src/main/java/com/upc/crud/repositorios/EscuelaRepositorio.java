package com.upc.crud.repositorios;

import com.upc.crud.entidades.Escuela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscuelaRepositorio extends JpaRepository<Escuela, Long> {
    List<Escuela> findByNombreContainingIgnoreCase(String nombre);
}
