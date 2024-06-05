package com.example.comentarios.repositorios;

import com.example.comentarios.entidades.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario,String> {
    List<Comentario> findAllByHotelId(int idHotel);

    List<Comentario> findAllByUsuarioId(int idUsuario);

    List<Comentario> findAllByReservaId(int idReserva);
}
