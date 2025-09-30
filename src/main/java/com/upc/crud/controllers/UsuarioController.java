package com.upc.crud.controllers;

import com.upc.crud.dto.UsuarioDTO;
import com.upc.crud.dto.ActividadDTO;
import com.upc.crud.interfaces.IUsuarioService;
import com.upc.crud.interfaces.IActividadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Validated
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final IActividadService actividadService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO created = usuarioService.insertar(dto);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + created.getIdUsuario()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable @Min(1) Long id) {
        Optional<UsuarioDTO> res = usuarioService.buscarPorId(id);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/rol")
    public ResponseEntity<UsuarioDTO> actualizarRol(@PathVariable @Min(1) Long id,
                                                    @RequestParam String rol) {
        Optional<UsuarioDTO> res = usuarioService.actualizarRol(id, rol);
        return res.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Min(1) Long id) {
        return usuarioService.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<UsuarioDTO> listar(@RequestParam(required = false, name = "q") String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return usuarioService.listarPorNombre(nombre);
        }
        return usuarioService.listar();
    }
    @GetMapping("/buscar/email")
    public ResponseEntity<UsuarioDTO> porEmail(@RequestParam String email) {
        UsuarioDTO dto = usuarioService.buscarPorEmail(email);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public List<UsuarioDTO> listarPorEscuelaYRol(@RequestParam Long escuelaId, @RequestParam String rol) {
        return usuarioService.listarPorEscuelaYRol(escuelaId, rol);
    }

    @PostMapping("/{id}/actividades")
    public ResponseEntity<ActividadDTO> crearActividad(
            @PathVariable("id") Long usuarioId,
            @RequestBody ActividadDTO dto
    ) {
        ActividadDTO creada = usuarioService.crearActividadParaUsuario(usuarioId, dto);
        return ResponseEntity
                .created(URI.create("/api/actividades/" + creada.getIdActividad()))
                .body(creada);
    }

    @PatchMapping("/actividades/{id}/completar")
    public ResponseEntity<ActividadDTO> completarActividad(@PathVariable @Min(1) Long id) {
        ActividadDTO completed = usuarioService.marcarActividadComoCompletada(id);
        return ResponseEntity.ok(completed);
    }

    @GetMapping("/{id}/actividades")
    public ResponseEntity<List<ActividadDTO>> listarActividades(
            @PathVariable Long id,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta
    ) {
        List<ActividadDTO> actividades = usuarioService.listarActividades(id, estado, desde, hasta);
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }
}
