package com.upc.crud.controllers;

import com.upc.crud.dto.ResultadoDTO;
import com.upc.crud.interfaces.IResultadoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
@Validated
public class ResultadoController {

    private final IResultadoService resultadoService;

    @PostMapping
    public ResponseEntity<ResultadoDTO> crear(@Valid @RequestBody ResultadoDTO dto) {
        ResultadoDTO created = resultadoService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/resultados/" + created.getIdResultado()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<ResultadoDTO> res = resultadoService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoDTO> actualizar(@PathVariable @Min(1) Long id,
                                                   @Valid @RequestBody ResultadoDTO dto) {
        Optional<ResultadoDTO> res = resultadoService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return resultadoService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ResultadoDTO> listar() {
        return resultadoService.listar();
    }

    @GetMapping("/proyecto/{proyectoId}/metrics")
    public Map<String, Object> metrics(@PathVariable @Min(1) Long proyectoId) {
        return resultadoService.metricsPorProyecto(proyectoId);
    }
}
