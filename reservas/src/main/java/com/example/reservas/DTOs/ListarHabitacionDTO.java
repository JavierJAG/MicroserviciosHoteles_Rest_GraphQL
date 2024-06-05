package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListarHabitacionDTO {
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private int habitacion_id;
}
