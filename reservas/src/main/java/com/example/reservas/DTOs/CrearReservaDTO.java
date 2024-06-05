package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearReservaDTO {
    private String nombre;
    private String contrasena;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int habitacion_id;
}
