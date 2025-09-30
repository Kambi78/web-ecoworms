package com.upc.crud.dto;

import com.upc.crud.entidades.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Usuario.Rol rol;
    private Long idEscuela;
}
