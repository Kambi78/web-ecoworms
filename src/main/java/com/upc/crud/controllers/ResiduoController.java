package com.upc.crud.controllers;

import com.upc.crud.dto.ResiduoDTO;
import com.upc.crud.interfaces.IResiduoService;
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
@RequestMapping("/api/residuos")
@RequiredArgsConstructor
@Validated
public class ResiduoController {

    private final IResiduoService residuoService;

    @PostMapping
    public ResponseEntity<ResiduoDTO> crear(@Valid @RequestBody ResiduoDTO dto) {
        ResiduoDTO created = residuoService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/residuos/" + created.getIdResiduos()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResiduoDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<ResiduoDTO> res = residuoService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResiduoDTO> actualizar(@PathVariable @Min(1) Long id,
                                                 @Valid @RequestBody ResiduoDTO dto) {
        Optional<ResiduoDTO> res = residuoService.editar(id, dto);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return residuoService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<ResiduoDTO> listar(@RequestParam(required = false, name = "q") String tipo) {
        if (tipo != null && !tipo.isBlank()) {
            return residuoService.listarPorTipo(tipo);
        }
        return residuoService.listar();
    }
}
