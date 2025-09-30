package com.upc.crud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResiduoDTO {

    private Long idResiduos;
    private String tipo;
    private Integer cantidad;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Long idEscuela;
    private String escuelaNombre;

    private Long idActividad;
    private String actividadNombre;
}
