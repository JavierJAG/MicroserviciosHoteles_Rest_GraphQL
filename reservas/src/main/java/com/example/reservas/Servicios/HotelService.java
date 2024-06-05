package com.example.reservas.Servicios;

import com.example.reservas.DTOs.ActualizarHotelDTO;
import com.example.reservas.DTOs.CrearHotelDTO;
import com.example.reservas.Entidades.Hotel;
import com.example.reservas.Repositorios.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    public Hotel crearHotel(CrearHotelDTO crearHotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setDireccion(crearHotelDTO.getDireccion());
        hotel.setNombre(crearHotelDTO.getNombreHotel());
        return hotelRepository.save(hotel);
    }

    public Hotel actualizarHotel(ActualizarHotelDTO actualizarHotelDTO) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(actualizarHotelDTO.getHotelID());

        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.setNombre(actualizarHotelDTO.getNombreHotel());
            hotel.setDireccion(actualizarHotelDTO.getDireccion());
            return hotelRepository.save(hotel);
        } else {
            return null;
        }
    }


    public void eliminarHotelPorID(int idHotel) {
        hotelRepository.deleteById(idHotel);
    }

    public Hotel obtenerIdApartirNombre(String nombreHotel) {
        return hotelRepository.findByNombre(nombreHotel);
    }

    public Hotel obtenerNombreAPartirId(int idHotel) {
        Optional<Hotel> hotel= hotelRepository.findById(idHotel);
        if (hotel.isPresent()){
            Hotel hotel1 = hotel.get();
            return hotel1;
        }else {
            return null;
        }
    }
}
