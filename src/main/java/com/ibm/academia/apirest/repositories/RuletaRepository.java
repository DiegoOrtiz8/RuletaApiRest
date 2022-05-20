package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.models.entities.Ruleta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("repositorioRuletas")
public interface RuletaRepository extends CrudRepository<Ruleta, Integer> {
}
