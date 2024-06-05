package com.example.reservas.Controladores;

import com.example.reservas.DTOs.*;
import com.example.reservas.Entidades.Reserva;
import com.example.reservas.Servicios.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    ReservaService reservaService;

    @PostMapping
    public ResponseEntity<String> crearReserva(@RequestBody CrearReservaDTO crearReservaDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(crearReservaDTO.getNombre(), crearReservaDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Reserva reserva = reservaService.crearReserva(crearReservaDTO);
                if (reserva == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido crear la reserva, datos no válidos");
                } else {
                    return ResponseEntity.ok("Reserva creada correctamente");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la reserva");
            }
        }
    }

    @PatchMapping
    public ResponseEntity<String> cambiarEstado(@RequestBody CambiarEstadoDTO cambiarEstadoDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(cambiarEstadoDTO.getNombre(), cambiarEstadoDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Reserva reserva = reservaService.cambiarEstado(cambiarEstadoDTO);
                if (reserva == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido modificar la reserva");
                } else {
                    return ResponseEntity.ok("Reserva modificada correctamente");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al modificar la reserva");
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<ListarHabitacionDTO>> listarReservasUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
            return null;
        } else {
            String obtenerID = "http://localhost:8702/usuarios/info/nombre/" + usuarioDTO.getNombre();
            ResponseEntity<String> idUsuario = restTemplate.getForEntity(obtenerID, String.class);
            int id = Integer.parseInt(idUsuario.getBody());
            try {
                List<ListarHabitacionDTO> reservaList = reservaService.listarReservas(id);
                if (reservaList.isEmpty()) {
                    ResponseEntity.ok("No hay reservas a nombre de este usuario");
                    return null;
                } else {

                    return ResponseEntity.ok(reservaList);
                }
            } catch (Exception e) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las reservas");
                return null;
            }
        }
    }

    @GetMapping("/{estado}")
    public ResponseEntity<List<ListarHabitacionDTO>> listarReservasSegunEstado(@PathVariable String estado, @RequestBody UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
            return null;
        } else {
            try {
                List<ListarHabitacionDTO> reservas = reservaService.listarReservasSegunEstado(estado);
                if (reservas.isEmpty()) {
                    return ResponseEntity.ok(null);
                } else {
                    return ResponseEntity.ok(reservas);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @GetMapping("/check/{idUsuario}-{idHotel}-{idReserva}")
    public ResponseEntity<Boolean> checkReserva(@PathVariable int idUsuario, @PathVariable int idHotel, @PathVariable int idReserva) {
        try {
            CheckReservaDTO checkReservaDTO = new CheckReservaDTO(idUsuario, idHotel, idReserva);
            Boolean resultado = reservaService.checkReserva(checkReservaDTO);

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

}