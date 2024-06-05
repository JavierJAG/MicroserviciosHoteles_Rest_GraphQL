package com.example.comentarios.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioConParametroDTO {
    private String nombre;
    private String contrasena;
    private String parametro;
}
