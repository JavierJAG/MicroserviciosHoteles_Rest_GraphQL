package com.example.reservas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarEstadoDTO {
    private String nombre;
    private String contrasena;
    private int reserva_id;
    private String estado;
}
