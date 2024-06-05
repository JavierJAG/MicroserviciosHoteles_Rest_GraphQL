package com.example.usuarios.repositorios;

import com.example.usuarios.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombreAndContrasena(String nombre, String contrasena);

    Usuario findByNombre(String nombre);
}
