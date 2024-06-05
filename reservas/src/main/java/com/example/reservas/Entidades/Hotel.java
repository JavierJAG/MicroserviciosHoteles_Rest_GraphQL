package com.example.reservas.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private int hotelId;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "direccion", length = 255)
    private String direccion;
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones;

}
