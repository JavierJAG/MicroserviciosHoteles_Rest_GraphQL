package com.example.comentarios.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoComentarioDTO {
    private String nombre;
    private String contrasena;
    private String nombreHotel;
    private int id_Reserva;
    private Double puntuacion;
    private String comentario;
}
