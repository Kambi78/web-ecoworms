package com.upc.crud.servicios;

import com.upc.crud.dto.ResiduoDTO;
import com.upc.crud.entidades.Residuo;
import com.upc.crud.repositorios.ResiduoRepositorio;
import com.upc.crud.interfaces.IResiduoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResiduoService implements IResiduoService {

    @Autowired
    private ResiduoRepositorio residuoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResiduoDTO insertar(ResiduoDTO dto) {
        Residuo residuo = modelMapper.map(dto, Residuo.class);
        Residuo residuoGuardado = residuoRepositorio.save(residuo);
        return modelMapper.map(residuoGuardado, ResiduoDTO.class);
    }

    @Override
    public Optional<ResiduoDTO> buscarPorId(Long id) {
        return residuoRepositorio.findById(id)
                .map(residuo -> modelMapper.map(residuo, ResiduoDTO.class));
    }

    @Override
    public Optional<ResiduoDTO> editar(Long id, ResiduoDTO dto) {
        return residuoRepositorio.findById(id).map(existing -> {
            existing.setTipo(dto.getTipo());
            existing.setCantidad(dto.getCantidad());
            existing.setFecha(dto.getFecha());
            Residuo residuoActualizado = residuoRepositorio.save(existing);
            return modelMapper.map(residuoActualizado, ResiduoDTO.class);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        if (residuoRepositorio.existsById(id)) {
            residuoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ResiduoDTO> listar() {
        List<Residuo> residuos = residuoRepositorio.findAll();
        return residuos.stream()
                .map(residuo -> modelMapper.map(residuo, ResiduoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResiduoDTO> listarPorTipo(String tipo) {
        List<Residuo> residuos = residuoRepositorio.findByTipoContainingIgnoreCase(tipo);
        return residuos.stream()
                .map(residuo -> modelMapper.map(residuo, ResiduoDTO.class))
                .collect(Collectors.toList());
    }
}
