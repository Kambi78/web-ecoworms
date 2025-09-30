package com.upc.crud.controllers;

import com.upc.crud.dto.EntrevistaDTO;
import com.upc.crud.interfaces.IEntrevistaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrevistas")
@RequiredArgsConstructor
@Validated
public class EntrevistaController {

    private final IEntrevistaService entrevistaService;

    @PostMapping
    public ResponseEntity<EntrevistaDTO> crear(@Valid @RequestBody EntrevistaDTO dto) {
        EntrevistaDTO created = entrevistaService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/entrevistas/" + created.getIdEntrevista()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrevistaDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<EntrevistaDTO> res = entrevistaService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrevistaDTO> actualizar(@PathVariable @Min(1) Long id,
                                                    @Valid @RequestBody EntrevistaDTO dto) {
        Optional<EntrevistaDTO> res = entrevistaService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return entrevistaService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EntrevistaDTO> listar(@RequestParam(required = false) Long usuarioId,
                                      @RequestParam(required = false) LocalDate desde,
                                      @RequestParam(required = false) LocalDate hasta) {
        if (usuarioId != null) {
            return entrevistaService.listarPorUsuario(usuarioId);
        }
        if (desde != null && hasta != null) {
            return entrevistaService.listarPorFecha(desde, hasta);
        }
        return entrevistaService.listar();
    }
}
