package com.upc.crud.interfaces;

import com.upc.crud.dto.UsuarioDTO;
import com.upc.crud.dto.ActividadDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    UsuarioDTO insertar(UsuarioDTO dto);
    Optional<UsuarioDTO> buscarPorId(Long id);
    Optional<UsuarioDTO> editar(Long id, UsuarioDTO dto);
    boolean eliminar(Long id);
    List<UsuarioDTO> listar();
    List<UsuarioDTO> listarPorNombre(String nombre);
    UsuarioDTO buscarPorEmail(String email);
    Optional<UsuarioDTO> actualizarRol(Long id, String rol);
    List<UsuarioDTO> listarPorEscuelaYRol(Long escuelaId, String rol);
    ActividadDTO crearActividadParaUsuario(Long usuarioId, ActividadDTO actividadDTO);
    ActividadDTO marcarActividadComoCompletada(Long actividadId);
    List<ActividadDTO> listarActividades(Long usuarioId, String estado, String desde, String hasta);
}
