package com.example.reservas.Controladores;

import com.example.reservas.DTOs.ActualizarHabitacionDTO;
import com.example.reservas.DTOs.AnadirHabitacionDTO;
import com.example.reservas.DTOs.UsuarioDTO;
import com.example.reservas.Entidades.Habitacion;
import com.example.reservas.Servicios.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/reservas")
public class HabitacionController {

    @Autowired
    HabitacionService habitacionService;

    @PostMapping("/habitacion")
    public ResponseEntity<String> crearHabitacion(@RequestBody AnadirHabitacionDTO anadirHabitacionDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(anadirHabitacionDTO.getNombre(), anadirHabitacionDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
            if (!Objects.equals(response.getBody(), true)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
            } else {
                Habitacion habitacion = habitacionService.crearHabitacion(anadirHabitacionDTO);
                if (habitacion != null) {
                    return ResponseEntity.ok("Habitación creada con éxito");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la habitación");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la habitación");
        }

    }

    @PatchMapping("/habitacion")
    public ResponseEntity<String> actualizarHabitacion(@RequestBody ActualizarHabitacionDTO actualizarHabitacionDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(actualizarHabitacionDTO.getNombre(), actualizarHabitacionDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
            if (!Objects.equals(response.getBody(), true)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
            } else {

                Habitacion habitacion = habitacionService.actualizarHabitacion(actualizarHabitacionDTO);
                if (habitacion != null) {
                    return ResponseEntity.ok("Habitación modificada con éxito");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La habitación introducida y/o su hotetel no existen");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al modificar la habitación");
        }

    }

    @DeleteMapping("/habitacion/{habitacion_id}")
    public ResponseEntity<String> eliminarHabitacion(@PathVariable int habitacion_id, @RequestBody UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
            if (!Objects.equals(response.getBody(), true)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
            } else {
                habitacionService.eliminarHabitacion(habitacion_id);
                return ResponseEntity.ok("Habitación eliminada con éxito");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido eliminar la habitacion");
        }
    }

}
