package com.upc.crud.interfaces;

import com.upc.crud.dto.ResultadoDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IResultadoService {
    ResultadoDTO insertar(ResultadoDTO dto);
    Optional<ResultadoDTO> buscarPorId(Long id);
    Optional<ResultadoDTO> editar(Long id, ResultadoDTO dto);
    boolean eliminar(Long id);
    List<ResultadoDTO> listar();
    List<ResultadoDTO> listarPorProyecto(Long proyectoId);
    List<ResultadoDTO> listarPorEscuela(Long escuelaId);
    Map<String, Object> metricsPorProyecto(Long proyectoId);
}
