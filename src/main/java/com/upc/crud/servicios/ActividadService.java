package com.upc.crud.servicios;

import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.entidades.Actividad;
import com.upc.crud.entidades.Usuario;
import com.upc.crud.entidades.Programa;
import com.upc.crud.repositorios.ActividadRepositorio;
import com.upc.crud.repositorios.ProgramaRepositorio;
import com.upc.crud.repositorios.UsuarioRepositorio;
import com.upc.crud.interfaces.IActividadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActividadService implements IActividadService {

    private final ActividadRepositorio actividadRepositorio;
    private final ProgramaRepositorio programaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ModelMapper modelMapper;

    @Override
    public ActividadDTO insertar(ActividadDTO dto) {
        Actividad actividad = modelMapper.map(dto, Actividad.class);

        Programa programa = programaRepositorio.findById(dto.getProgramaId())
                .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
        actividad.setPrograma(programa);

        Usuario usuario = usuarioRepositorio.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        actividad.setUsuario(usuario);

        if (actividad.getMateriales() == null) actividad.setMateriales(new HashMap<>());

        Actividad saved = actividadRepositorio.save(actividad);
        return modelMapper.map(saved, ActividadDTO.class);
    }

    @Override
    public List<ActividadDTO> listar() {
        List<Actividad> actividades = actividadRepositorio.findAll();
        return actividades.stream()
                .map(actividad -> modelMapper.map(actividad, ActividadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActividadDTO> listarActividades(Long programaId, String equipo, String fechaInicio, String fechaFin) {
        String eq = (equipo == null || equipo.isBlank()) ? null : equipo.trim();
        LocalDate ini = (fechaInicio == null || fechaInicio.isBlank()) ?
                null : LocalDate.parse(fechaInicio);
        LocalDate fin = (fechaFin == null || fechaFin.isBlank()) ?
                null : LocalDate.parse(fechaFin);

        if (ini != null && fin != null && fin.isBefore(ini)) {LocalDate tmp = ini;ini = fin;fin = tmp;}

        List<Actividad> actividades = actividadRepositorio.findByProgramaAndFilters(programaId, eq, ini, fin);
        return actividades.stream()
                .map(a -> {
                    ActividadDTO d = modelMapper.map(a, ActividadDTO.class);
                    if (d.getIdActividad() == null) {d.setIdActividad(a.getId()); }
                    Long progId = (a.getPrograma() != null) ? a.getPrograma().getIdPrograma() : programaId;
                    d.setProgramaId(progId);
                    return d;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<ActividadDTO> listarPorUsuario(Long usuarioId) {
        List<Actividad> actividades = actividadRepositorio.findByUsuario_IdUsuario(usuarioId);
        return actividades.stream()
                .map(actividad -> modelMapper.map(actividad, ActividadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActividadDTO> listarPorPrograma(Long programaId) {
        List<Actividad> actividades = actividadRepositorio.findByPrograma_IdPrograma(programaId);
        return actividades.stream()
                .map(actividad -> modelMapper.map(actividad, ActividadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ActividadDTO completar(Long id) {
        Actividad actividad = actividadRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividad.setEstado("Completada");
        Actividad updatedActividad = actividadRepositorio.save(actividad);
        return modelMapper.map(updatedActividad, ActividadDTO.class);
    }

    @Override
    public Optional<ActividadDTO> buscarPorId(Long id) {
        return actividadRepositorio.findById(id)
                .map(actividad -> modelMapper.map(actividad, ActividadDTO.class));
    }

    @Override
    public Optional<ActividadDTO> editar(Long id, ActividadDTO dto) {
        return actividadRepositorio.findById(id).map(existing -> {
            existing.setEquipo(dto.getEquipo());
            existing.setFecha(dto.getFecha());
            existing.setMateriales(dto.getMateriales());
            existing.setPesoTotal(dto.getPesoTotal());

            Programa programa = programaRepositorio.findById(dto.getProgramaId())
                    .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
            existing.setPrograma(programa);

            Actividad updatedActividad = actividadRepositorio.save(existing);
            return modelMapper.map(updatedActividad, ActividadDTO.class);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (actividadRepositorio.existsById(id)) {
            actividadRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Long> obtenerResumen(Long programaId) {
        List<Object[]> rows = actividadRepositorio.countMaterialesPorPrograma(programaId);
        return rows.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

}
