package com.upc.crud.repositorios;

import com.upc.crud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByEscuela_IdEscuelaAndRol(Long escuelaId, Usuario.Rol rol);
    List<Usuario> findAll();
}
