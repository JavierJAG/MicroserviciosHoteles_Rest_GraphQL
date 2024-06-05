package com.example.reservas.Servicios;

import com.example.reservas.DTOs.*;
import com.example.reservas.Entidades.Estado;
import com.example.reservas.Entidades.Habitacion;
import com.example.reservas.Entidades.Hotel;
import com.example.reservas.Entidades.Reserva;
import com.example.reservas.Repositorios.HabitacionRepository;
import com.example.reservas.Repositorios.HotelRepository;
import com.example.reservas.Repositorios.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    HabitacionRepository habitacionRepository;
    @Autowired
    HotelRepository hotelRepository;

    public Reserva crearReserva(CrearReservaDTO crearReservaDTO) {
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(crearReservaDTO.getFecha_inicio());
        reserva.setFechaFin(crearReservaDTO.getFecha_fin());
        reserva.setEstado("pendiente");
        Optional<Habitacion> habitacion = habitacionRepository.findById(crearReservaDTO.getHabitacion_id());
        if (habitacion.isPresent()) {
            reserva.setHabitacion(habitacion.get());
            return reservaRepository.save(reserva);
        } else {
            return null;
        }
    }

    public Reserva cambiarEstado(CambiarEstadoDTO cambiarEstadoDTO) {
        Optional<Reserva> optionalReserva = reservaRepository.findById(cambiarEstadoDTO.getReserva_id());
        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            String estado = cambiarEstadoDTO.getEstado().toLowerCase();
            try {
                Estado.valueOf(estado);
                reserva.setEstado(estado);
                reservaRepository.save(reserva);
                return reserva;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<ListarHabitacionDTO> listarReservas(int idUsuario) {
        List<Reserva> reservas = reservaRepository.findAllByUsuarioId(idUsuario);
        List<ListarHabitacionDTO> listarHabitacionDTOS = new ArrayList<>();
        for (Reserva reserva : reservas) {
            LocalDate fechaInicio = reserva.getFechaInicio();
            LocalDate fechaFin = reserva.getFechaFin();
            int habitacionID = reserva.getHabitacion().getHabitacionId();
            ListarHabitacionDTO listarHabitacionDTO = new ListarHabitacionDTO(fechaInicio, fechaFin, habitacionID);
            listarHabitacionDTOS.add(listarHabitacionDTO);
        }
        return listarHabitacionDTOS;
    }

    public List<ListarHabitacionDTO> listarReservasSegunEstado(String estado) {
        List<ListarHabitacionDTO> listarHabitacionDTOS = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByEstado(estado);
        for (Reserva reserva : reservas) {
            LocalDate fechaInicio = reserva.getFechaInicio();
            LocalDate fechaFin = reserva.getFechaFin();
            int habitacionID = reserva.getHabitacion().getHabitacionId();
            ListarHabitacionDTO listarHabitacionDTO = new ListarHabitacionDTO(fechaInicio, fechaFin, habitacionID);
            listarHabitacionDTOS.add(listarHabitacionDTO);
        }
        return listarHabitacionDTOS;
    }

    public Boolean checkReserva(CheckReservaDTO checkReservaDTO) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(checkReservaDTO.getIdReserva());

        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();

            if (reserva.getUsuarioId() == checkReservaDTO.getIdUsuario()) {
                Optional<Hotel> hotelOptional = hotelRepository.findById(checkReservaDTO.getIdHotel());

                if (hotelOptional.isPresent()) {
                    Hotel hotel = hotelOptional.get();

                    return hotel.getHotelId() == reserva.getHabitacion().getHotel().getHotelId();
                }
            }
        }

        return false;
    }

}
