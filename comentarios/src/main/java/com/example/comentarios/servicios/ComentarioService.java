package com.example.comentarios.servicios;

import com.example.comentarios.DTOs.UsuarioConParametroDTO;
import com.example.comentarios.DTOs.InfoComentarioDTO;
import com.example.comentarios.DTOs.UsuarioDTO;
import com.example.comentarios.entidades.Comentario;
import com.example.comentarios.repositorios.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;

    public InfoComentarioDTO crearComentario(InfoComentarioDTO crearComentarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        UsuarioDTO usuarioDTO = new UsuarioDTO(crearComentarioDTO.getNombre(), crearComentarioDTO.getContrasena());
        String obtenerIDUsuario = "http://localhost:8702/usuarios/info/nombre/" + crearComentarioDTO.getNombre();
        String obtenerIDHotel = "http://localhost:8701/reservas/hotel/id/{idHotel}";
        String idHotel = crearComentarioDTO.getNombreHotel();
        try {
            ResponseEntity<String> responseUsuario = restTemplate.getForEntity(obtenerIDUsuario, String.class);
            String idUsuario = responseUsuario.getBody();
            ResponseEntity<String> responseHotel = restTemplate.postForEntity(obtenerIDHotel, usuarioDTO, String.class, idHotel);
            String idHotel1 = responseHotel.getBody();
            String checkingReserva = "http://localhost:8701/reservas/check/" + idUsuario + "-" + idHotel1 + "-" + crearComentarioDTO.getId_Reserva();
            ResponseEntity<Boolean> responseReserva = restTemplate.getForEntity(checkingReserva, Boolean.class);
            Boolean existeReserva = responseReserva.getBody();

            if (existeReserva != null && existeReserva) {
                List<Comentario> comentarios = comentarioRepository.findAll();
                boolean existeComentario = false;
                for (Comentario comentario : comentarios) {
                    if (comentario.getHotelId() == Integer.parseInt(idHotel1) && comentario.getUsuarioId() == Integer.parseInt(idUsuario) && comentario.getReservaId() == crearComentarioDTO.getId_Reserva()) {
                        existeComentario = true;
                        break;
                    }
                }
                if (existeComentario) {
                    return null;
                } else {
                    Comentario comentario = new Comentario();
                    comentario.setHotelId(Integer.parseInt(idHotel1));
                    comentario.setUsuarioId(Integer.parseInt(idUsuario));
                    comentario.setPuntuacion(crearComentarioDTO.getPuntuacion());
                    comentario.setReservaId(crearComentarioDTO.getId_Reserva());
                    comentario.setComentario(crearComentarioDTO.getComentario());
                    String fecha = String.valueOf(LocalDate.now());
                    comentario.setFechaCreacion(fecha);
                    comentarioRepository.save(comentario);
                    return crearComentarioDTO;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean eliminarComentarios() {
        try {
            comentarioRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarComentarioUsuario(UsuarioConParametroDTO usuarioConParametroDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String obtenerID = "http://localhost:8702/usuarios/info/nombre/" + usuarioConParametroDTO.getNombre();
        ResponseEntity<String> responseUsuario = restTemplate.getForEntity(obtenerID, String.class);
        if (responseUsuario.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        int idUsuario = Integer.parseInt(responseUsuario.getBody());
        Optional<Comentario> optionalComentario = comentarioRepository.findById(usuarioConParametroDTO.getParametro());
        if (optionalComentario.isPresent()) {
            Comentario comentario = optionalComentario.get();
            if (comentario.getUsuarioId() == idUsuario) {
                comentarioRepository.delete(comentario);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<InfoComentarioDTO> listarComentariosHotel(UsuarioConParametroDTO usuarioConParametroDTO) {
        RestTemplate restTemplate = new RestTemplate();
        List<InfoComentarioDTO> comentarioDTOS = new ArrayList<>();
        String obtenerIDHotel = "http://localhost:8701/reservas/hotel/id/{idHotel}";
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        ResponseEntity<String> responseHotel = restTemplate.postForEntity(obtenerIDHotel, usuarioDTO, String.class, usuarioConParametroDTO.getParametro());
        int idHotel = Integer.parseInt(responseHotel.getBody());
        List<Comentario> comentariosHotel = comentarioRepository.findAllByHotelId(idHotel);
        if (comentariosHotel.isEmpty()) {
            return null;
        } else {
            for (Comentario comentario : comentariosHotel) {
                InfoComentarioDTO infoComentarioDTO = new InfoComentarioDTO();
                infoComentarioDTO.setNombreHotel(usuarioConParametroDTO.getParametro());
                infoComentarioDTO.setPuntuacion(comentario.getPuntuacion());
                infoComentarioDTO.setComentario(comentario.getComentario());
                infoComentarioDTO.setId_Reserva(comentario.getReservaId());
                comentarioDTOS.add(infoComentarioDTO);
            }
            return comentarioDTOS;
        }
    }

    public List<InfoComentarioDTO> listarComentariosUsuario(UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        List<InfoComentarioDTO> comentarioDTOS = new ArrayList<>();
        String obtenerIDUsuario = "http://localhost:8702/usuarios/info/nombre/{nombre}";
        ResponseEntity<String> response = restTemplate.getForEntity(obtenerIDUsuario, String.class, usuarioDTO.getNombre());
        int idUsuario = Integer.parseInt(response.getBody());
        List<Comentario> comentariosHotel = comentarioRepository.findAllByUsuarioId(idUsuario);
        if (comentariosHotel.isEmpty()) {
            return null;
        } else {
            for (Comentario comentario : comentariosHotel) {
                InfoComentarioDTO infoComentarioDTO = new InfoComentarioDTO();
                String obtenerNombreHotel = "http://localhost:8701/reservas/hotel/nombre/{idHotel}";
                int idHotel = comentario.getHotelId();
                ResponseEntity<String> responseHotel = restTemplate.postForEntity(obtenerNombreHotel, usuarioDTO, String.class, idHotel);
                System.out.println(2);
                infoComentarioDTO.setNombreHotel(String.valueOf(responseHotel.getBody()));
                infoComentarioDTO.setPuntuacion(comentario.getPuntuacion());
                infoComentarioDTO.setComentario(comentario.getComentario());
                infoComentarioDTO.setId_Reserva(comentario.getReservaId());
                comentarioDTOS.add(infoComentarioDTO);
            }
            return comentarioDTOS;
        }
    }

    public List<InfoComentarioDTO> mostrarComentarioUsuarioReserva(UsuarioConParametroDTO usuarioConParametroDTO) {
        int idReserva = Integer.parseInt(usuarioConParametroDTO.getParametro());
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
        RestTemplate restTemplate = new RestTemplate();
        String obtenerIDUsuario = "http://localhost:8702/usuarios/info/nombre/{nombre}";
        ResponseEntity<String> response = restTemplate.getForEntity(obtenerIDUsuario, String.class, usuarioDTO.getNombre());
        int idUsuario = Integer.parseInt(response.getBody());
        List<InfoComentarioDTO> comentarioDTOS = new ArrayList<>();
        List<Comentario> comentariosHotel = comentarioRepository.findAllByReservaId(idReserva);
        if (comentariosHotel.isEmpty()) {
            return null;
        } else {
            for (Comentario comentario : comentariosHotel) {
                if (comentario.getUsuarioId()==idUsuario) {
                    InfoComentarioDTO infoComentarioDTO = new InfoComentarioDTO();
                    String obtenerNombreHotel = "http://localhost:8701/reservas/hotel/nombre/{idHotel}";
                    int idHotel = comentario.getHotelId();
                    ResponseEntity<String> responseHotel = restTemplate.postForEntity(obtenerNombreHotel, usuarioDTO, String.class, idHotel);
                    infoComentarioDTO.setNombreHotel(String.valueOf(responseHotel.getBody()));
                    infoComentarioDTO.setPuntuacion(comentario.getPuntuacion());
                    infoComentarioDTO.setComentario(comentario.getComentario());
                    infoComentarioDTO.setId_Reserva(comentario.getReservaId());
                    comentarioDTOS.add(infoComentarioDTO);
                }
            }
           if (comentarioDTOS.isEmpty()){
               return null;
           }else {
               return comentarioDTOS;
           }
        }
    }

    public Double puntuacionMediaHotel(UsuarioConParametroDTO usuarioConParametroDTO) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String obtenerIDHotel = "http://localhost:8701/reservas/hotel/id/{idHotel}";
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioConParametroDTO.getNombre(), usuarioConParametroDTO.getContrasena());
            ResponseEntity<String> responseHotel = restTemplate.postForEntity(obtenerIDHotel, usuarioDTO, String.class, usuarioConParametroDTO.getParametro());
            int idHotel = Integer.parseInt(responseHotel.getBody());
           List<Comentario> comentarios = comentarioRepository.findAllByHotelId(idHotel);
           Double suma = 0.0;
           for (Comentario comentario: comentarios) {
               suma += comentario.getPuntuacion();
           }
           if (comentarios.isEmpty()){
               return 0.0;
           }else {
               return suma/comentarios.size();
           }
        }catch (Exception e){
            return null;
        }
    }

    public Double puntuacionesMediasUsuario(UsuarioDTO usuarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String obtenerIDUsuario = "http://localhost:8702/usuarios/info/nombre/{nombre}";
        ResponseEntity<String> response = restTemplate.getForEntity(obtenerIDUsuario, String.class, usuarioDTO.getNombre());
        int usuarioID = Integer.parseInt(response.getBody());
        List<Comentario> comentarios = comentarioRepository.findAllByUsuarioId(usuarioID);
        Double suma = 0.0;
        for (Comentario comentario: comentarios) {
            suma += comentario.getPuntuacion();
        }
        if (comentarios.isEmpty()){
            return 0.0;
        }else {
            return suma/comentarios.size();
        }
    }
}
