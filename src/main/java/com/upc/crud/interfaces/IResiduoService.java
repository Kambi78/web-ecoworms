package com.upc.crud.interfaces;

import com.upc.crud.dto.ResiduoDTO;

import java.util.List;
import java.util.Optional;

public interface IResiduoService {
    ResiduoDTO insertar(ResiduoDTO dto);
    Optional<ResiduoDTO> buscarPorId(Long id);
    Optional<ResiduoDTO> editar(Long id, ResiduoDTO dto);
    boolean eliminar(Long id);
    List<ResiduoDTO> listar();
    List<ResiduoDTO> listarPorTipo(String tipo);
}
