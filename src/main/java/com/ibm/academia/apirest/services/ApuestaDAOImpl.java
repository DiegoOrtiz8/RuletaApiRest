package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.mapper.ApuestaMapper;
import com.ibm.academia.apirest.models.dto.ApuestaDTO;
import com.ibm.academia.apirest.models.entities.Apuesta;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.repositories.ApuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ApuestaDAOImpl extends GenericoDAOImpl<Apuesta, ApuestaRepository> implements ApuestaDAO {

    @Autowired
    public ApuestaDAOImpl(@Qualifier("repositorioApuestas")ApuestaRepository repository) {
        super(repository);
    }

    @Override
    public List<ApuestaDTO> obtenerApuestaRuleta(Integer ruletaId) {

        List<ApuestaDTO> listaApuestas = new ArrayList<>();
        List<Apuesta> apuestasRuleta = (List<Apuesta>) repository.obtenerApuestaDeRuleta(ruletaId);

        if(apuestasRuleta.isEmpty())
            throw new NotFoundException("No existen apuestas realizadas en la ruleta");

        listaApuestas = apuestasRuleta.stream()
                .map(ApuestaMapper::mapApuesta)
                .collect(Collectors.toList());

        return listaApuestas;
    }

    @Override
    public Apuesta validarApuesta(Double dineroApuesta, Ruleta ruleta, Integer tipoApuesta, String seleccionApuesta) {

        Random random = new Random();
        Integer numeroAleatorio = 0;
        Integer numeroApuesta = 0;
        String colorApuesta = "";

        if(ruleta.getEstado() != 1)
            throw new BadRequestException("No se pueden realizar apuestas, ya que la ruleta se encuentra cerrada");

        if(dineroApuesta <= 0 || dineroApuesta > 10000)
            throw new BadRequestException("La apuesta debe de ser mayor a 0 o menor a 10000");

        Apuesta apuesta = new Apuesta();
        apuesta.setDinero(dineroApuesta);
        apuesta.setRuleta(ruleta);

        if(tipoApuesta == 1) {
            numeroAleatorio = random.ints(0, 36).findFirst().getAsInt();
            numeroApuesta = Integer.parseInt(seleccionApuesta);

            if(numeroApuesta >= 0 || numeroApuesta <= 36) {
                if(numeroAleatorio == numeroApuesta) {
                    apuesta.setEstado(1);
                }
                else {
                    apuesta.setEstado(0);
                }
            }
            else
                throw new BadRequestException("Apuesta invalida");

            apuesta.setApuestaRealizada(seleccionApuesta);
            apuesta.setResultado(String.valueOf(numeroAleatorio));
            apuesta = repository.save(apuesta);
        }

        else if(tipoApuesta == 2) {
            numeroAleatorio = random.ints(1, 3).findFirst().getAsInt();
            colorApuesta = seleccionApuesta;

            if(numeroAleatorio == 1) {
                apuesta.setResultado("ROJO");
                if(colorApuesta.toUpperCase().equals("ROJO")) {
                    apuesta.setEstado(1);
                }
                else if(colorApuesta.toUpperCase().equals("NEGRO")) {
                    apuesta.setEstado(0);
                }
                else {
                    throw new BadRequestException("Apuesta invalida");
                }
            }
            else if(numeroAleatorio == 2) {
                apuesta.setResultado("NEGRO");
                if(colorApuesta.toUpperCase().equals("NEGRO")) {
                    apuesta.setEstado(1);
                }
                else if(colorApuesta.toUpperCase().equals("ROJO")) {
                    apuesta.setEstado(0);
                }
                else {
                    throw new BadRequestException("Apuesta invalida");
                }
            }
            else {
                throw new BadRequestException("Apuesta invalida");
            }

            apuesta.setApuestaRealizada(colorApuesta.toUpperCase());
            apuesta = repository.save(apuesta);
        }
        else {
            throw new BadRequestException("Apuesta invalida");
        }

        return apuesta;
    }
}
