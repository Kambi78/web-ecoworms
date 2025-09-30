package com.upc.crud.servicios;

import com.upc.crud.dto.EscuelaDTO;
import com.upc.crud.entidades.Escuela;
import com.upc.crud.repositorios.EscuelaRepositorio;
import com.upc.crud.interfaces.IEscuelaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EscuelaService implements IEscuelaService {

    @Autowired
    private EscuelaRepositorio escuelaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public EscuelaDTO insertar(EscuelaDTO dto) {
        Escuela escuela = modelMapper.map(dto, Escuela.class);
        Escuela escuelaGuardada = escuelaRepositorio.save(escuela);
        return modelMapper.map(escuelaGuardada, EscuelaDTO.class);
    }

    @Override
    @Transactional
    public Optional<EscuelaDTO> buscarPorId(Long id) {
        return escuelaRepositorio.findById(id)
                .map(escuela -> modelMapper.map(escuela, EscuelaDTO.class));
    }

    @Override
    @Transactional
    public Optional<EscuelaDTO> editar(Long id, EscuelaDTO dto) {
        return escuelaRepositorio.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setDireccion(dto.getDireccion());
            existing.setTipo(dto.getTipo());
            Escuela escuelaActualizada = escuelaRepositorio.save(existing);
            return modelMapper.map(escuelaActualizada, EscuelaDTO.class);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        if (escuelaRepositorio.existsById(id)) {
            escuelaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<EscuelaDTO> listar() {
        List<Escuela> escuelas = escuelaRepositorio.findAll();
        return escuelas.stream()
                .map(escuela -> modelMapper.map(escuela, EscuelaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EscuelaDTO> listarPorNombre(String nombre) {
        List<Escuela> escuelas = escuelaRepositorio.findByNombreContainingIgnoreCase(nombre);
        return escuelas.stream()
                .map(escuela -> modelMapper.map(escuela, EscuelaDTO.class))
                .collect(Collectors.toList());
    }
}
