package com.upc.crud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {
    private Long idResultado;
    private String observaciones;
    private BigDecimal porcentajeReduccion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Long idEscuela;
    private Long idProyecto;

    private String escuelaNombre;
    private String proyectoNombre;

}
