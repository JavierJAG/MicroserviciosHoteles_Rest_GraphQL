package com.example.usuarios.controladores;

import com.example.usuarios.DTOs.UsuarioDTO;
import com.example.usuarios.entidades.Usuario;
import com.example.usuarios.servidores.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(("/registrar"))
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario nuevoUsuario) {
        try {
            usuarioService.crearAutor(nuevoUsuario);
            return ResponseEntity.ok("Usuario creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar un usuario: " + e.getMessage());
        }

    }

    @PutMapping("/registrar")
    public ResponseEntity<String> actualizarUsuario(@RequestBody Usuario usuarioActualizado) {
        try {
            usuarioService.actualizarUsuario(usuarioActualizado);
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> eliminarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.findUsuarioByNombreAndContrasena(usuarioDTO.getNombre(), usuarioDTO.getContrasena());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Los datos introducidos no coinciden con los de ningún usuario");
        } else {
            try {
                usuarioService.eliminarUsuario(usuario);
                return ResponseEntity.ok("Usuario eliminado con éxito");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido eliminar el usuario: " + e.getMessage());
            }
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.findUsuarioByNombreAndContrasena(usuarioDTO.getNombre(), usuarioDTO.getContrasena());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/info/id/{id}")
    public ResponseEntity<String> obtenerInfoUsuarioPorID(@PathVariable Integer id) {
        Usuario usuario = usuarioService.obtenerInfoUsuarioPorID(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un usuario con el id indicado");
        } else {
            return ResponseEntity.ok(usuario.getNombre());
        }
    }

    @GetMapping("/info/nombre/{nombre}")
    public ResponseEntity<String> obtenerInfoUsuarioPorNombre(@PathVariable String nombre) {
        Usuario usuario = usuarioService.obtenerInfoUsuarioPorNombre(nombre);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un usuario con el nombre indicado");
        } else {
            return ResponseEntity.ok(String.valueOf(usuario.getUsuario_id()));
        }
    }

    @GetMapping("/checkIfExists/{id}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable int id) {
        Usuario usuario = usuarioService.obtenerInfoUsuarioPorID(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }
}
