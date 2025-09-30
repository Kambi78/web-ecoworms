package com.upc.crud.controllers;

import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.servicios.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    @PostMapping
    public ResponseEntity<ActividadDTO> crearActividad(@RequestBody ActividadDTO actividadDTO) {
        ActividadDTO saved = actividadService.insertar(actividadDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ActividadDTO>> listar() {
        List<ActividadDTO> actividades = actividadService.listar();
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ActividadDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<ActividadDTO> actividades = actividadService.listarPorUsuario(usuarioId);
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    @GetMapping("/programa/{programaId}")
    public ResponseEntity<List<ActividadDTO>> listarPorPrograma(@PathVariable Long programaId) {
        List<ActividadDTO> actividades = actividadService.listarPorPrograma(programaId);
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<ActividadDTO> completar(@PathVariable Long id) {
        ActividadDTO actividadCompletada = actividadService.completar(id);
        return new ResponseEntity<>(actividadCompletada, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ActividadDTO>> buscarPorId(@PathVariable Long id) {
        Optional<ActividadDTO> actividad = actividadService.buscarPorId(id);
        return actividad.isPresent() ?
                new ResponseEntity<>(actividad, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadDTO> editar(@PathVariable Long id, @RequestBody ActividadDTO dto) {
        Optional<ActividadDTO> actividadEditada = actividadService.editar(id, dto);
        return actividadEditada.isPresent() ?
                new ResponseEntity<>(actividadEditada.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = actividadService.eliminar(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/resumen/{programaId}")
    public ResponseEntity<Map<String, Long>> obtenerResumen(@PathVariable Long programaId) {
        Map<String, Long> resumen = actividadService.obtenerResumen(programaId);
        return new ResponseEntity<>(resumen, HttpStatus.OK);
    }
}
