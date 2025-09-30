package com.upc.crud.interfaces;

import com.upc.crud.dto.EscuelaDTO;
import java.util.List;
import java.util.Optional;

public interface IEscuelaService {
    EscuelaDTO insertar(EscuelaDTO dto);
    Optional<EscuelaDTO> buscarPorId(Long id);
    Optional<EscuelaDTO> editar(Long id, EscuelaDTO dto);
    boolean eliminar(Long id);

    List<EscuelaDTO> listar();
    List<EscuelaDTO> listarPorNombre(String nombre);
}
