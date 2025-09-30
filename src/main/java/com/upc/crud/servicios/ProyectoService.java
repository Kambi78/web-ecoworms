package com.upc.crud.servicios;

import com.upc.crud.dto.ProyectoDTO;
import com.upc.crud.dto.ResultadoDTO;
import com.upc.crud.entidades.Proyecto;
import com.upc.crud.entidades.Escuela;
import com.upc.crud.entidades.Programa;
import com.upc.crud.entidades.Resultado;
import com.upc.crud.repositorios.ProyectoRepositorio;
import com.upc.crud.repositorios.ResultadoRepositorio;
import com.upc.crud.interfaces.IProyectoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProyectoService implements IProyectoService {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Autowired
    private ResultadoRepositorio resultadoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProyectoDTO insertar(ProyectoDTO dto) {
        Proyecto proyecto = modelMapper.map(dto, Proyecto.class);
        Proyecto proyectoGuardado = proyectoRepositorio.save(proyecto);
        return modelMapper.map(proyectoGuardado, ProyectoDTO.class);
    }

    public ProyectoDTO obtenerPorId(Long id) {
        Proyecto proyecto = proyectoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        ProyectoDTO proyectoDTO = modelMapper.map(proyecto, ProyectoDTO.class);
        Escuela escuela = proyecto.getEscuela();
        Programa programa = proyecto.getPrograma();
        proyectoDTO.setEscuelaNombre(escuela != null ? escuela.getNombre() : null);
        proyectoDTO.setProgramaNombre(programa != null ? programa.getNombre() : null);

        return proyectoDTO;
    }

    @Override
    public Optional<ProyectoDTO> buscarPorId(Long id) {
        return proyectoRepositorio.findById(id)
                .map(proyecto -> modelMapper.map(proyecto, ProyectoDTO.class));
    }

    @Override
    public Optional<ProyectoDTO> editar(Long id, ProyectoDTO dto) {
        return proyectoRepositorio.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setDescripcion(dto.getDescripcion());
            existing.setFechaInicio(dto.getFechaInicio());
            existing.setFechaFin(dto.getFechaFin());
            Proyecto proyectoActualizado = proyectoRepositorio.save(existing);
            return modelMapper.map(proyectoActualizado, ProyectoDTO.class);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (proyectoRepositorio.existsById(id)) {
            proyectoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ProyectoDTO> listar() {
        List<Proyecto> proyectos = proyectoRepositorio.findAll();
        return proyectos.stream()
                .map(proyecto -> modelMapper.map(proyecto, ProyectoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProyectoDTO> listarPorNombre(String nombre) {
        List<Proyecto> proyectos = proyectoRepositorio.findByNombreContainingIgnoreCase(nombre);
        return proyectos.stream()
                .map(proyecto -> modelMapper.map(proyecto, ProyectoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultadoDTO> listarResultados(Long idProyecto) {
        Proyecto proyecto = proyectoRepositorio.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        List<Resultado> resultados = resultadoRepositorio.findByProyecto_IdProyecto(idProyecto);

        return resultados.stream()
                .map(resultado -> modelMapper.map(resultado, ResultadoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> obtenerMetrics(Long idProyecto) {
        List<Resultado> resultados = resultadoRepositorio.findByProyecto_IdProyecto(idProyecto);

        long totalResultados = resultados.size();

        BigDecimal totalPorcentajeReduccion = resultados.stream()
                .map(Resultado::getPorcentajeReduccion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("totalResultados", totalResultados);
        metrics.put("totalPorcentajeReduccion", totalPorcentajeReduccion.longValue());

        return metrics;
    }
}
