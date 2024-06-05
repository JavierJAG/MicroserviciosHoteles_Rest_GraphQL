package com.example.usuarios.servidores;

import com.example.usuarios.entidades.Usuario;
import com.example.usuarios.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario crearAutor(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario actualizarUsuario(Usuario usuarioActualizado) {
        return usuarioRepository.save(usuarioActualizado);
    }

    public Usuario findUsuarioByNombreAndContrasena(String nombre, String contrasena) {
        Usuario usuario = usuarioRepository.findByNombreAndContrasena(nombre, contrasena);
        return usuario;
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario obtenerInfoUsuarioPorID(int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuario obtenerInfoUsuarioPorNombre(String nombre) {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        return usuario;
    }
}
