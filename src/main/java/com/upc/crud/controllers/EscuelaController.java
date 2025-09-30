package com.upc.crud.controllers;

import com.upc.crud.dto.EscuelaDTO;
import com.upc.crud.interfaces.IEscuelaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/escuelas")
@RequiredArgsConstructor
@Validated
public class EscuelaController {

    private final IEscuelaService escuelaService;

    @PostMapping
    public ResponseEntity<EscuelaDTO> crear(@Valid @RequestBody EscuelaDTO dto) {
        EscuelaDTO created = escuelaService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/escuelas/" + created.getIdEscuela()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscuelaDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<EscuelaDTO> res = escuelaService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscuelaDTO> actualizar(@PathVariable @Min(1) Long id,
                                                 @Valid @RequestBody EscuelaDTO dto) {
        Optional<EscuelaDTO> res = escuelaService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return escuelaService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EscuelaDTO> listar(@RequestParam(required = false, name = "q") String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return escuelaService.listarPorNombre(nombre);
        }
        return escuelaService.listar();
    }
}
