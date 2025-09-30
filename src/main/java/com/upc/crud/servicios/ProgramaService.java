package com.upc.crud.servicios;

import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.dto.ProgramaDTO;
import com.upc.crud.entidades.Actividad;
import com.upc.crud.entidades.Escuela;
import com.upc.crud.entidades.Programa;
import com.upc.crud.repositorios.ActividadRepositorio;
import com.upc.crud.repositorios.EscuelaRepositorio;
import com.upc.crud.repositorios.ProgramaRepositorio;
import com.upc.crud.interfaces.IProgramaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramaService implements IProgramaService {

    private final ProgramaRepositorio programaRepositorio;
    private final ActividadRepositorio actividadRepositorio;
    private final EscuelaRepositorio escuelaRepositorio;
    private final ModelMapper modelMapper;

    @Override
    public ProgramaDTO insertar(ProgramaDTO dto) {
        Programa programa = modelMapper.map(dto, Programa.class);
        Programa savedPrograma = programaRepositorio.save(programa);
        return modelMapper.map(savedPrograma, ProgramaDTO.class);
    }

    @Override
    public Optional<ProgramaDTO> buscarPorId(Long id) {
        return programaRepositorio.findById(id)
                .map(programa -> modelMapper.map(programa, ProgramaDTO.class));
    }

    @Override
    @Transactional
    public Optional<ProgramaDTO> editar(Long id, ProgramaDTO dto) {
        return programaRepositorio.findById(id).map(p -> {
            if (dto.getIdEscuela() != null) {
                Escuela escuela = escuelaRepositorio.findById(dto.getIdEscuela()).orElseThrow(()
                        -> new IllegalArgumentException("La escuela no existe: " + dto.getIdEscuela()));
                p.setEscuela(escuela);
            }

            if (dto.getNombre() != null)       p.setNombre(dto.getNombre());
            if (dto.getDescripcion() != null)  p.setDescripcion(dto.getDescripcion());
            if (dto.getFechaInicio() != null)  p.setFechaInicio(dto.getFechaInicio());
            if (dto.getFechaFin() != null)     p.setFechaFin(dto.getFechaFin());

            if (p.getFechaInicio() != null && p.getFechaFin() != null && p.getFechaFin().isBefore(p.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha fin no puede ser menor que la fecha inicio");
            }

            Programa actualizado = programaRepositorio.save(p);
            ProgramaDTO out = modelMapper.map(actualizado, ProgramaDTO.class);
            out.setIdEscuela(actualizado.getEscuela().getIdEscuela());
            return out;
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (programaRepositorio.existsById(id)) {
            programaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProgramaDTO> listar() {
        List<Programa> programas = programaRepositorio.findAll();
        return programas.stream()
                .map(programa -> modelMapper.map(programa, ProgramaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgramaDTO> listarPorNombre(String nombre) {
        List<Programa> programas = programaRepositorio.findByNombreContainingIgnoreCase(nombre);
        return programas.stream()
                .map(programa -> modelMapper.map(programa, ProgramaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ActividadDTO registrarActividad(Long programaId, ActividadDTO actividadDTO) {
        Programa programa = programaRepositorio.findById(programaId)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        Actividad actividad = modelMapper.map(actividadDTO, Actividad.class);
        actividad.setPrograma(programa);

        Actividad savedActividad = actividadRepositorio.save(actividad);
        return modelMapper.map(savedActividad, ActividadDTO.class);
    }

    public List<ActividadDTO> listarActividades(Long programaId, String equipo, String fechaInicio, String fechaFin) {
        String eq = Optional.ofNullable(equipo).map(String::trim).filter(s -> !s.isEmpty())
                .orElse(null);

        LocalDate ini = (fechaInicio != null && !fechaInicio.isBlank()) ? LocalDate.parse(fechaInicio) : null;
        LocalDate fin = (fechaFin != null && !fechaFin.isBlank()) ? LocalDate.parse(fechaFin) : null;

        if (ini != null && fin != null && fin.isBefore(ini)) {
            LocalDate tmp = ini;
            ini = fin;
            fin = tmp;
        }

        List<Actividad> actividades = actividadRepositorio.findByProgramaAndFilters(programaId, eq, ini, fin);
        return actividades.stream()
                .map(a -> {
                    ActividadDTO dto = new ActividadDTO();
                    dto.setIdActividad(a.getId());
                    dto.setProgramaId(
                            (a.getPrograma() != null) ? a.getPrograma().getIdPrograma() : programaId
                    );
                    dto.setEquipo(a.getEquipo());
                    dto.setFecha(a.getFecha());
                    dto.setEstado(a.getEstado());
                    dto.setCantidad(a.getCantidad());
                    dto.setPesoTotal(a.getPesoTotal());
                    dto.setMateriales(a.getMateriales());
                    return dto;
                })
                .collect(Collectors.toList());
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
