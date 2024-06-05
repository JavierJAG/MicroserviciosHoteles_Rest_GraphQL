package com.example.reservas.Repositorios;

import com.example.reservas.DTOs.UsuarioDTO;
import com.example.reservas.Entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByEstado(String estado);

    List<Reserva> findAllByUsuarioId(int idUsuario);
}
