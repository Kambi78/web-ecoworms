package com.upc.crud.repositorios;

import com.upc.crud.entidades.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepositorio extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
    List<Proyecto> findAll();
}
