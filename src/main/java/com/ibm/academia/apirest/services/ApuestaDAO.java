package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.dto.ApuestaDTO;
import com.ibm.academia.apirest.models.entities.Apuesta;
import com.ibm.academia.apirest.models.entities.Ruleta;

import java.util.List;

public interface ApuestaDAO extends GenericoDAO<Apuesta> {
    public List<ApuestaDTO> obtenerApuestaRuleta(Integer ruletaId);
    public Apuesta validarApuesta(Double dineroApuesta, Ruleta ruleta, Integer tipoApuesta, String seleccionApuesta);
}
