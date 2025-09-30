package com.upc.crud.servicios;

import com.upc.crud.dto.UsuarioDTO;
import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.entidades.Usuario;
import com.upc.crud.entidades.Actividad;
import com.upc.crud.entidades.Programa;
import com.upc.crud.repositorios.UsuarioRepositorio;
import com.upc.crud.repositorios.ActividadRepositorio;
import com.upc.crud.repositorios.ProgramaRepositorio;
import com.upc.crud.interfaces.IUsuarioService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ActividadRepositorio actividadRepositorio;

    @Autowired
    private ProgramaRepositorio programaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO insertar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }

    @Override
    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    @Override
    public Optional<UsuarioDTO> editar(Long id, UsuarioDTO dto) {
        return usuarioRepositorio.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setEmail(dto.getEmail());
            existing.setRol(dto.getRol());
            Usuario usuarioActualizado = usuarioRepositorio.save(existing);
            return modelMapper.map(usuarioActualizado, UsuarioDTO.class);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (usuarioRepositorio.existsById(id)) {
            usuarioRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepositorio.findByNombreContainingIgnoreCase(nombre);
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        return usuario != null ? modelMapper.map(usuario, UsuarioDTO.class) : null;
    }

    @Override
    public Optional<UsuarioDTO> actualizarRol(Long id, String rol) {
        return usuarioRepositorio.findById(id).map(existing -> {
            Usuario.Rol rolEnum = Usuario.Rol.valueOf(rol.toUpperCase());
            existing.setRol(rolEnum);

            Usuario usuarioActualizado = usuarioRepositorio.save(existing);
            return modelMapper.map(usuarioActualizado, UsuarioDTO.class);
        });
    }


    @Override
    public List<UsuarioDTO> listarPorEscuelaYRol(Long escuelaId, String rol) {
        Usuario.Rol rolEnum = Usuario.Rol.valueOf(rol.toUpperCase());
        List<Usuario> usuarios = usuarioRepositorio.findByEscuela_IdEscuelaAndRol(escuelaId, rolEnum);
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ActividadDTO crearActividadParaUsuario(Long usuarioId, ActividadDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getProgramaId() == null) {
            throw new IllegalArgumentException("programaId es obligatorio");
        }

        Programa programa = programaRepositorio.findById(dto.getProgramaId())
                .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        Actividad entidad = modelMapper.map(dto, Actividad.class);
        entidad.setUsuario(usuario);
        entidad.setPrograma(programa);

        Actividad saved = actividadRepositorio.save(entidad);

        ActividadDTO resp = modelMapper.map(saved, ActividadDTO.class);
        resp.setProgramaId(saved.getPrograma().getIdPrograma());
        resp.setUsuarioId(saved.getUsuario().getIdUsuario());
        return resp;
    }


    @Override
    public ActividadDTO marcarActividadComoCompletada(Long actividadId) {
        Actividad actividad = actividadRepositorio.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        if (actividad.getFecha().isAfter(LocalDate.now())) {
            throw new RuntimeException("No se puede marcar como completada antes de la fecha programada");
        }

        actividad.setEstado("Completada");
        Actividad updatedActividad = actividadRepositorio.save(actividad);
        return modelMapper.map(updatedActividad, ActividadDTO.class);
    }

    @Override
    public List<ActividadDTO> listarActividades(Long usuarioId, String estado, String desde, String hasta) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Actividad> actividades = actividadRepositorio.findByUsuario_IdUsuario(usuarioId);
        return actividades.stream()
                .filter(a -> estado == null || a.getEstado().equalsIgnoreCase(estado))
                .filter(a -> {
                    if (desde == null) return true;
                    return !a.getFecha().isBefore(LocalDate.parse(desde));
                })
                .filter(a -> {
                    if (hasta == null) return true;
                    return !a.getFecha().isAfter(LocalDate.parse(hasta));
                })
                .map(a -> modelMapper.map(a, ActividadDTO.class))
                .collect(Collectors.toList());
    }
}
