package com.example.reservas.Controladores;

import com.example.reservas.DTOs.ActualizarHotelDTO;
import com.example.reservas.DTOs.CrearHotelDTO;
import com.example.reservas.DTOs.UsuarioDTO;
import com.example.reservas.Entidades.Hotel;
import com.example.reservas.Servicios.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/reservas/hotel")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @PostMapping
    public ResponseEntity<String> crearHotel(@RequestBody CrearHotelDTO crearHotelDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(crearHotelDTO.getNombre(), crearHotelDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Hotel hotel = hotelService.crearHotel(crearHotelDTO);
                if (hotel == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido crear el hotel");
                } else {
                    return ResponseEntity.ok("Hotel creado correctamente");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el hotel");
            }
        }
    }

    @PatchMapping
    public ResponseEntity<String> actualizarHotel(@RequestBody ActualizarHotelDTO actualizarHotelDTO) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(actualizarHotelDTO.getNombre(), actualizarHotelDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Hotel hotel = hotelService.actualizarHotel(actualizarHotelDTO);
                if (hotel == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se ha podido actualizar el hotel");
                } else {
                    return ResponseEntity.ok("Hotel actualizado correctamente");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el hotel");
            }
        }
    }
    @DeleteMapping("/{idHotel}")
    public ResponseEntity<String>eliminarHotel(@PathVariable int idHotel, @RequestBody UsuarioDTO usuarioDTO){
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                hotelService.eliminarHotelPorID(idHotel);
                return ResponseEntity.ok("Hotel eliminado correctamente");
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el hotel");
            }
        }
    }
    @PostMapping("/id/{nombreHotel}")
    public ResponseEntity<String> obtenerIdApartirNombre (@PathVariable String nombreHotel, @RequestBody UsuarioDTO usuarioDTO){
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Hotel hotel = hotelService.obtenerIdApartirNombre(nombreHotel);
                if (hotel!=null){
                    return ResponseEntity.ok(String.valueOf(hotel.getHotelId()));
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un hotel por ese nombre");
                }
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el hotel");
            }
        }
    }
    @PostMapping("/nombre/{idHotel}")
    public ResponseEntity<String> obtenerNombreAPartirId (@PathVariable int idHotel, @RequestBody UsuarioDTO usuarioDTO){
        RestTemplate restTemplate = new RestTemplate();
        String servicioUsuarios = "http://localhost:8702/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(servicioUsuarios, usuarioDTO, Boolean.class);
        if (!Objects.equals(response.getBody(), true)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario introducido no es correcto");
        } else {
            try {
                Hotel hotel = hotelService.obtenerNombreAPartirId(idHotel);
                if (hotel!=null){
                    return ResponseEntity.ok(hotel.getNombre());
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un hotel por ese id");
                }
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el hotel");
            }
        }
    }
}
