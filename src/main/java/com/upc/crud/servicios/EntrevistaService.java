package com.upc.crud.servicios;

import com.upc.crud.dto.EntrevistaDTO;
import com.upc.crud.entidades.Entrevista;
import com.upc.crud.repositorios.EntrevistaRepositorio;
import com.upc.crud.interfaces.IEntrevistaService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntrevistaService implements IEntrevistaService {

    @Autowired
    private EntrevistaRepositorio entrevistaRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public EntrevistaDTO insertar(EntrevistaDTO dto) {
        Entrevista entrevista = modelMapper.map(dto, Entrevista.class);
        Entrevista entrevistaGuardada = entrevistaRepositorio.save(entrevista);
        return modelMapper.map(entrevistaGuardada, EntrevistaDTO.class);
    }

    @Override
    @Transactional
    public Optional<EntrevistaDTO> buscarPorId(Long id) {
        return entrevistaRepositorio.findById(id)
                .map(entrevista -> modelMapper.map(entrevista, EntrevistaDTO.class));
    }

    @Override
    @Transactional
    public Optional<EntrevistaDTO> editar(Long id, EntrevistaDTO dto) {
        return entrevistaRepositorio.findById(id).map(existing -> {
            existing.setFecha(dto.getFecha());
            existing.setPreguntas(dto.getPreguntas());
            existing.setRespuestas(dto.getRespuestas());
            Entrevista entrevistaActualizada = entrevistaRepositorio.save(existing);
            return modelMapper.map(entrevistaActualizada, EntrevistaDTO.class);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        if (entrevistaRepositorio.existsById(id)) {
            entrevistaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<EntrevistaDTO> listar() {
        List<Entrevista> entrevistas = entrevistaRepositorio.findAll();
        return entrevistas.stream()
                .map(entrevista -> modelMapper.map(entrevista, EntrevistaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EntrevistaDTO> listarPorUsuario(Long usuarioId) {
        List<Entrevista> entrevistas = entrevistaRepositorio.findByUsuarioIdUsuario(usuarioId);
        return entrevistas.stream()
                .map(entrevista -> modelMapper.map(entrevista, EntrevistaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EntrevistaDTO> listarPorFecha(LocalDate desde, LocalDate hasta) {
        List<Entrevista> entrevistas = entrevistaRepositorio.findByFechaBetween(desde, hasta);
        return entrevistas.stream()
                .map(entrevista -> modelMapper.map(entrevista, EntrevistaDTO.class))
                .collect(Collectors.toList());
    }
}

