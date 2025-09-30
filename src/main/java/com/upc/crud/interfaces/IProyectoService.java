package com.upc.crud.interfaces;

import com.upc.crud.dto.ProyectoDTO;
import com.upc.crud.dto.ResultadoDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProyectoService {
    ProyectoDTO insertar(ProyectoDTO dto);
    Optional<ProyectoDTO> buscarPorId(Long id);
    Optional<ProyectoDTO> editar(Long id, ProyectoDTO dto);
    boolean eliminar(Long id);
    ProyectoDTO obtenerPorId(Long id);
    List<ProyectoDTO> listar();
    List<ProyectoDTO> listarPorNombre(String nombre);
    List<ResultadoDTO> listarResultados(Long idProyecto);
    Map<String, Long> obtenerMetrics(Long idProyecto);
}
