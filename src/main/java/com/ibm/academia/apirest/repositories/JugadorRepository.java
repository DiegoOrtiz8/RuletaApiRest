package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.models.entities.Jugador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("repositorioJugadores")
public interface JugadorRepository extends CrudRepository<Jugador, Integer> {
}
