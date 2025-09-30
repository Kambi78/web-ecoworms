package com.upc.crud.repositorios;

import com.upc.crud.entidades.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResiduoRepositorio extends JpaRepository<Residuo, Long> {
    List<Residuo> findByTipoContainingIgnoreCase(String tipo);
    List<Residuo> findAll();
}
