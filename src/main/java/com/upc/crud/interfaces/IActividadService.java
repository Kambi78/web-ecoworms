package com.upc.crud.interfaces;

import com.upc.crud.dto.ActividadDTO;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IActividadService {
    ActividadDTO insertar(ActividadDTO dto);
    Optional<ActividadDTO> buscarPorId(Long id);
    Optional<ActividadDTO> editar(Long id, ActividadDTO dto);
    boolean eliminar(Long id);
    List<ActividadDTO> listar();
    List<ActividadDTO> listarActividades(Long programaId, String equipo, String fechaInicio, String fechaFin);
    List<ActividadDTO> listarPorUsuario(Long usuarioId);
    List<ActividadDTO> listarPorPrograma(Long programaId);
    ActividadDTO completar(Long idActividad);
    Map<String, Long> obtenerResumen(Long programaId);
}
