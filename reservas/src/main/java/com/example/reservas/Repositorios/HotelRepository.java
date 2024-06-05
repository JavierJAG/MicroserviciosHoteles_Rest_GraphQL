package com.example.reservas.Repositorios;

import com.example.reservas.Entidades.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository <Hotel,Integer> {
    Hotel findByNombre(String nombreHotel);
}
