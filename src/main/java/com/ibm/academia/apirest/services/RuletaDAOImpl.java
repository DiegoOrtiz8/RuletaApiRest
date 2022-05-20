package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Ruleta;
import com.ibm.academia.apirest.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuletaDAOImpl extends GenericoDAOImpl<Ruleta, RuletaRepository> implements RuletaDAO {

    @Autowired
    public RuletaDAOImpl(@Qualifier("repositorioRuletas")RuletaRepository repository) {
        super(repository);
    }

    @Override
    public Ruleta aperturaRuleta(Ruleta ruleta) {

        Ruleta ruletaAbierta = ruleta;

        if(ruletaAbierta.getEstado() == 0)
            throw new BadRequestException("La ruleta seleccionada ha sido cerrada previamente");
        else {
            ruletaAbierta.setEstado(1);
            return repository.save(ruletaAbierta);
        }

    }

    @Override
    public Ruleta cierreRuleta(Ruleta ruleta) {

        Ruleta ruletaAbierta = ruleta;

        if(ruletaAbierta.getEstado() == 1) {
            ruletaAbierta.setEstado(0);

            return repository.save(ruletaAbierta);
        }
        else if (ruletaAbierta.getEstado() == 0) {
            throw new BadRequestException("La ruleta ya ha sido cerrada");
        }
        else
            throw new BadRequestException("La ruleta tiene que abrirse primero para poder cerrarla");

    }

    @Override
    public Integer guadrar(Ruleta ruleta) {
        Ruleta ruletaGuardada = repository.save(ruleta);

        return ruletaGuardada.getId();
    }

    @Override
    public List<Ruleta> listarRuletas() {

        List<Ruleta> ruletas = (List<Ruleta>) repository.findAll();

        if(ruletas.isEmpty())
            throw new NotFoundException("No existen ruletas registradas");

        return ruletas;
    }
}
