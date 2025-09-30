package com.upc.crud.controllers;

import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.dto.ProgramaDTO;
import com.upc.crud.interfaces.IProgramaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/programas")
@RequiredArgsConstructor
@Validated
public class ProgramaController {

    private final IProgramaService programaService;

    @PostMapping
    public ResponseEntity<ProgramaDTO> crear(@Valid @RequestBody ProgramaDTO dto) {
        ProgramaDTO created = programaService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/programas/" + created.getIdPrograma()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<ProgramaDTO> res = programaService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaDTO> actualizar(@PathVariable @Min(1) Long id,
                                                  @Valid @RequestBody ProgramaDTO dto) {
        Optional<ProgramaDTO> res = programaService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return programaService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ProgramaDTO> listar(@RequestParam(required = false, name = "q") String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return programaService.listarPorNombre(nombre);
        }
        return programaService.listar();
    }

    @PostMapping("/{programaId}/actividades")
    public ResponseEntity<ActividadDTO> registrarActividad(@PathVariable Long programaId,
                                                           @Valid @RequestBody ActividadDTO actividadDTO) {
        ActividadDTO createdActividad = programaService.registrarActividad(programaId, actividadDTO);
        return ResponseEntity
                .created(URI.create("/api/programas/" + programaId + "/actividades/" + createdActividad.getIdActividad()))
                .body(createdActividad);
    }

    @GetMapping("/{programaId}/actividades")
    public ResponseEntity<List<ActividadDTO>> listarActividades(
            @PathVariable Long programaId,
            @RequestParam(required = false) String equipo,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        List<ActividadDTO> actividades =
                programaService.listarActividades(programaId, equipo, fechaInicio, fechaFin);
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/{programaId}/resumen")
    public ResponseEntity<Map<String, Long>> obtenerResumen(@PathVariable Long programaId) {
        Map<String, Long> resumen = programaService.obtenerResumen(programaId);
        return ResponseEntity.ok(resumen);
    }
}
