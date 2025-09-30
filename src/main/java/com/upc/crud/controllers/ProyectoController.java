package com.upc.crud.controllers;

import com.upc.crud.dto.ResultadoDTO;
import com.upc.crud.dto.ProyectoDTO;
import com.upc.crud.interfaces.IProyectoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
@Validated
public class ProyectoController {

    private final IProyectoService proyectoService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProyectoDTO> crear(@Valid @RequestBody ProyectoDTO dto) {
        ProyectoDTO created = proyectoService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/proyectos/" + created.getIdProyecto()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtener(@PathVariable @Min(1) Long id) {
        try {
            ProyectoDTO proyectoDTO = proyectoService.obtenerPorId(id);
            return ResponseEntity.ok(proyectoDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizar(@PathVariable @Min(1) Long id,
                                                  @Valid @RequestBody ProyectoDTO dto) {
        Optional<ProyectoDTO> res = proyectoService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return proyectoService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ProyectoDTO> listar(@RequestParam(required = false, name = "q") String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return proyectoService.listarPorNombre(nombre);
        }
        return proyectoService.listar();
    }

    @GetMapping("/{id}/resultados")
    public ResponseEntity<List<ResultadoDTO>> obtenerResultados(@PathVariable Long id) {
        List<ResultadoDTO> resultados = proyectoService.listarResultados(id);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{id}/resultados/metrics")
    public ResponseEntity<Map<String, Long>> obtenerMetrics(@PathVariable Long id) {
        Map<String, Long> metrics = proyectoService.obtenerMetrics(id);
        return ResponseEntity.ok(metrics);
    }
}
