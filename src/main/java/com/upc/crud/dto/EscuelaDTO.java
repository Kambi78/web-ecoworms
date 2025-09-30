package com.upc.crud.dto;

import com.upc.crud.entidades.Escuela;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscuelaDTO {
    private Long idEscuela;
    private String nombre;
    private String direccion;
    private Escuela.TipoEscuela tipo;
}
