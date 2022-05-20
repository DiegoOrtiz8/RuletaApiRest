package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.models.dto.ApuestaDTO;
import com.ibm.academia.apirest.models.entities.Apuesta;

public class ApuestaMapper {

    public static ApuestaDTO mapApuesta(Apuesta apuesta){
        ApuestaDTO apuestaDTO = new ApuestaDTO();

        apuestaDTO.setApuestaRealizada(apuesta.getApuestaRealizada());
        apuestaDTO.setResultado(apuesta.getResultado());
        apuestaDTO.setDinero(apuesta.getDinero());
        apuestaDTO.setId(apuesta.getId());
        if(apuesta.getEstado()==0)
            apuestaDTO.setEstado("Perdida");
        else
            apuestaDTO.setEstado("Ganada");

        return apuestaDTO;
    }
}
