package com.example.reservas.Servicios;

import com.example.reservas.DTOs.ActualizarHabitacionDTO;
import com.example.reservas.DTOs.AnadirHabitacionDTO;
import com.example.reservas.Entidades.Habitacion;
import com.example.reservas.Entidades.Hotel;
import com.example.reservas.Repositorios.HabitacionRepository;
import com.example.reservas.Repositorios.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HabitacionService {
    @Autowired
    HabitacionRepository habitacionRepository;
    @Autowired
    HotelRepository hotelRepository;

    public Habitacion crearHabitacion(AnadirHabitacionDTO habitacion) {
        Habitacion habitacion1 = new Habitacion();
        habitacion1.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        habitacion1.setTipo(habitacion.getTipo());
        habitacion1.setPrecio(habitacion.getPrecio());
        Optional<Hotel> hotel = hotelRepository.findById(habitacion.getIdHotel());
        if (hotel.isPresent()) {
            habitacion1.setHotel(hotel.get());
            habitacion1.setDisponible(true);
            return habitacionRepository.save(habitacion1);
        } else {
            return null;
        }
    }


    public Habitacion actualizarHabitacion(ActualizarHabitacionDTO habitacionDTO) {
        Optional<Habitacion> existeHabitacion = habitacionRepository.findById(habitacionDTO.getHabitacionId());
        if (existeHabitacion.isPresent()) {
            Habitacion habitacion = existeHabitacion.get();
            habitacion.setNumeroHabitacion(habitacionDTO.getNumeroHabitacion());
            habitacion.setTipo(habitacionDTO.getTipo());
            habitacion.setPrecio(habitacionDTO.getPrecio());
            Optional<Hotel> hotel = hotelRepository.findById(habitacionDTO.getIdHotel());
            if (hotel.isPresent()) {
                habitacion.setHotel(hotel.get());
                habitacion.setDisponible(habitacionDTO.getDisponible());
                return habitacionRepository.save(habitacion);
            } else {
                return null; // Si el hotel es null, devuelve null
            }
        } else {
            return null; // Si la habitación no existe, devuelve null
        }
    }


    public void eliminarHabitacion(int id) {
        habitacionRepository.deleteById(id);
    }
}
