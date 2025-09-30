package com.upc.crud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"usuarioId"}, allowGetters = true)
public class ActividadDTO {

    private Long idActividad;
    private String equipo;
    private Map<String, Integer> materiales;
    private Double pesoTotal;
    private int cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private String estado;
    private Long programaId;
    private Long usuarioId;
}