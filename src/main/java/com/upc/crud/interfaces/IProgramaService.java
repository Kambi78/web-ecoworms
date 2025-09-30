package com.upc.crud.interfaces;

import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.dto.ProgramaDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProgramaService {
    ProgramaDTO insertar(ProgramaDTO dto);
    Optional<ProgramaDTO> buscarPorId(Long id);
    Optional<ProgramaDTO> editar(Long id, ProgramaDTO dto);
    boolean eliminar(Long id);
    List<ProgramaDTO> listar();
    List<ProgramaDTO> listarPorNombre(String nombre);
    ActividadDTO registrarActividad(Long programaId, ActividadDTO actividadDTO);
    List<ActividadDTO> listarActividades(Long programaId, String equipo, String fechaInicio, String fechaFin);
    Map<String, Long> obtenerResumen(Long programaId);
}
