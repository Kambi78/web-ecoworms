package com.upc.crud.interfaces;

import com.upc.crud.dto.EntrevistaDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEntrevistaService {
    EntrevistaDTO insertar(EntrevistaDTO dto);
    Optional<EntrevistaDTO> buscarPorId(Long id);
    Optional<EntrevistaDTO> editar(Long id, EntrevistaDTO dto);
    boolean eliminar(Long id);
    List<EntrevistaDTO> listar();
    List<EntrevistaDTO> listarPorUsuario(Long usuarioId);
    List<EntrevistaDTO> listarPorFecha(LocalDate desde, LocalDate hasta);
}
