package com.upc.crud.servicios;

import com.upc.crud.dto.ResultadoDTO;
import com.upc.crud.entidades.Resultado;
import com.upc.crud.repositorios.ResultadoRepositorio;
import com.upc.crud.repositorios.EscuelaRepositorio;
import com.upc.crud.repositorios.ProyectoRepositorio;
import com.upc.crud.interfaces.IResultadoService;
import com.upc.crud.entidades.Proyecto;
import com.upc.crud.entidades.Escuela;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoService implements IResultadoService {

    @Autowired
    private ResultadoRepositorio resultadoRepositorio;
    @Autowired
    private EscuelaRepositorio escuelaRepositorio;
    @Autowired
    private ProyectoRepositorio proyectoRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResultadoDTO insertar(ResultadoDTO dto) {
        Escuela escuela = escuelaRepositorio.findById(dto.getIdEscuela())
                .orElseThrow(() -> new RuntimeException("Escuela no encontrada"));

        Proyecto proyecto = proyectoRepositorio.findById(dto.getIdProyecto())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Resultado resultado = modelMapper.map(dto, Resultado.class);
        resultado.setEscuela(escuela);
        resultado.setProyecto(proyecto);
        Resultado savedResultado = resultadoRepositorio.save(resultado);
        ResultadoDTO resultDTO = modelMapper.map(savedResultado, ResultadoDTO.class);
        resultDTO.setEscuelaNombre(escuela.getNombre());
        resultDTO.setProyectoNombre(proyecto.getNombre());

        return resultDTO;
    }

    @Override
    public Optional<ResultadoDTO> buscarPorId(Long id) {
        return resultadoRepositorio.findById(id)
                .map(resultado -> modelMapper.map(resultado, ResultadoDTO.class));
    }

    @Override
    public Optional<ResultadoDTO> editar(Long id, ResultadoDTO dto) {
        return resultadoRepositorio.findById(id).map(existing -> {
            existing.setObservaciones(dto.getObservaciones());
            existing.setPorcentajeReduccion(dto.getPorcentajeReduccion());
            existing.setFecha(dto.getFecha());

            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(dto.getIdProyecto());
            existing.setProyecto(proyecto);

            Escuela escuela = new Escuela();
            escuela.setIdEscuela(dto.getIdEscuela());
            existing.setEscuela(escuela);

            Resultado resultadoActualizado = resultadoRepositorio.save(existing);
            return modelMapper.map(resultadoActualizado, ResultadoDTO.class);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (resultadoRepositorio.existsById(id)) {
            resultadoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ResultadoDTO> listar() {
        List<Resultado> resultados = resultadoRepositorio.findAll();
        return resultados.stream()
                .map(resultado -> modelMapper.map(resultado, ResultadoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultadoDTO> listarPorProyecto(Long proyectoId) {
        List<Resultado> resultados = resultadoRepositorio.findByProyecto_IdProyecto(proyectoId);
        return resultados.stream()
                .map(resultado -> modelMapper.map(resultado, ResultadoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultadoDTO> listarPorEscuela(Long escuelaId) {
        List<Resultado> resultados = resultadoRepositorio.findByEscuela_IdEscuela(escuelaId);
        return resultados.stream()
                .map(resultado -> modelMapper.map(resultado, ResultadoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> metricsPorProyecto(Long proyectoId) {
        return Map.of("metric1", 100, "metric2", 200);
    }
}
