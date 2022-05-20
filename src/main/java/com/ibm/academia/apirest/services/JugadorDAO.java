package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Jugador;

public interface JugadorDAO {
    public Jugador actualizarDinero(Integer jugadorId, Double apuesta, Integer estadoApuesta);
}
