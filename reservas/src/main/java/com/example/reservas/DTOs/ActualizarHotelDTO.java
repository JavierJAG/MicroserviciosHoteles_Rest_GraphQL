package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarHotelDTO {
    private String nombre;
    private String contrasena;
    private int hotelID;
    private String nombreHotel;
    private String direccion;
}
