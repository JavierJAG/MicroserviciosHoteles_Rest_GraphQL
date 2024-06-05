package com.example.comentarios.controladores;

import com.example.comentarios.DTOs.UsuarioConParametroDTO;
import com.example.comentarios.DTOs.InfoComentarioDTO;
import com.example.comentarios.DTOs.UsuarioDTO;
import com.example.comentarios.servicios.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
    @Autowired
    ComentarioService comentarioService;

    @MutationMapping
    public InfoComentarioDTO crearComentario(@Argument InfoComentarioDTO crearComentarioDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(crearComentarioDTO.getNombre(), crearComentarioDTO.getContrasena());
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            InfoComentarioDTO comentario1 = comentarioService.crearComentario(crearComentarioDTO);
            if (comentario1 != null) {
                return comentario1;
            } else {
                return null;
            }
        }
    }

    @MutationMapping
    public ResponseEntity<String> eliminarComentarios() {
        if (comentarioService.eliminarComentarios()) {
            return ResponseEntity.ok("Se han eliminado todos los comentarios");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar los comentarios");
        }
    }

    @MutationMapping
    public ResponseEntity<String> eliminarComentarioDeUsuario(@Argument UsuarioConParametroDTO usuarioConParametroDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        if (!validarUsuario(usuarioDTO)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Datos de usuario errones");
        } else {
            try {
                if (comentarioService.eliminarComentarioUsuario(usuarioConParametroDTO)) {
                    return ResponseEntity.ok("Comentario eliminado correctamente");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido borrar el comentario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido borrar el comentario");
            }
        }
    }

    @QueryMapping
    public List<InfoComentarioDTO> listarComentariosHotel(@Argument UsuarioConParametroDTO usuarioConParametroDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            try {
                List<InfoComentarioDTO> comentarioDTOS = comentarioService.listarComentariosHotel(usuarioConParametroDTO);
                if (comentarioDTOS.isEmpty()) {
                    return null;
                } else {
                    return comentarioDTOS;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    @QueryMapping
    public List<InfoComentarioDTO> listarComentariosUsuario(@Argument UsuarioDTO usuarioDTO) {
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            try {
                List<InfoComentarioDTO> comentarioDTOS = comentarioService.listarComentariosUsuario(usuarioDTO);
                if (comentarioDTOS.isEmpty()) {
                    return null;
                } else {
                    return comentarioDTOS;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    @QueryMapping
    public List<InfoComentarioDTO> mostrarComentarioUsuarioReserva(@Argument UsuarioConParametroDTO usuarioConParametroDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            try {
                List<InfoComentarioDTO> comentarioDTOS = comentarioService.mostrarComentarioUsuarioReserva(usuarioConParametroDTO);
                if (comentarioDTOS.isEmpty()) {
                    return null;
                } else {
                    return comentarioDTOS;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    @QueryMapping
    public Double puntuacionMediaHotel(@Argument UsuarioConParametroDTO usuarioConParametroDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            try {
                return comentarioService.puntuacionMediaHotel(usuarioConParametroDTO);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @QueryMapping
    public Double puntuacionesMediasUsuario(@Argument UsuarioDTO usuarioDTO) {
        if (!validarUsuario(usuarioDTO)) {
            return null;
        } else {
            try {
                return comentarioService.puntuacionesMediasUsuario(usuarioDTO);
            } catch (Exception e) {
                return null;
            }
        }

    }

    public Boolean validarUsuario(UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return false;
        } else {
            return true;
        }
    }
}
