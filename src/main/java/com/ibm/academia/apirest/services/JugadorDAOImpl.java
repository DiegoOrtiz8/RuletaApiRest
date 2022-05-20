package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Jugador;
import com.ibm.academia.apirest.repositories.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JugadorDAOImpl extends GenericoDAOImpl<Jugador, JugadorRepository> implements JugadorDAO {

    @Autowired
    public JugadorDAOImpl(@Qualifier("repositorioJugadores")JugadorRepository repository) {
        super(repository);
    }

    @Override
    public Jugador actualizarDinero(Integer id, Double apuesta, Integer estadoApuesta) {

        Optional<Jugador> oJugador = repository.findById(id);

        Jugador jugadorActualizar = oJugador.get();

        if(estadoApuesta == 1)
            jugadorActualizar.setDinero(jugadorActualizar.getDinero() + apuesta);
        else
            jugadorActualizar.setDinero(jugadorActualizar.getDinero() - apuesta);

        jugadorActualizar = repository.save(jugadorActualizar);

        return jugadorActualizar;

    }
}
