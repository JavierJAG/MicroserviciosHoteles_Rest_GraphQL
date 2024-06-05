package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnadirHabitacionDTO {
    private String nombre;
    private String contrasena;
    private Integer numeroHabitacion;
    private String tipo;
    private BigDecimal precio;
    private int idHotel;
}
