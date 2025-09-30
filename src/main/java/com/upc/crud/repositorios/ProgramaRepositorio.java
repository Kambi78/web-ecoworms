package com.upc.crud.repositorios;

import com.upc.crud.entidades.Programa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramaRepositorio extends JpaRepository<Programa, Long> {
    List<Programa> findByNombreContainingIgnoreCase(String nombre);
    List<Programa> findAll();
}
