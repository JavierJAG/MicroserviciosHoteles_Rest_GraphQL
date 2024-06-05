package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearHotelDTO {
    private String nombre;
    private String contrasena;
    private String nombreHotel;
    private String direccion;
}
