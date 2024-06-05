package com.example.comentarios.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comentarios")
public class Comentario {
    @Id
    private String id;
    private int usuarioId;
    private int hotelId;
    private int reservaId;
    private double puntuacion;
    private String comentario;
    private String fechaCreacion;
}
