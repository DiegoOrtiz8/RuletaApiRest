package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Ruleta;

import java.util.List;

public interface RuletaDAO extends GenericoDAO<Ruleta>{

    public Ruleta aperturaRuleta(Ruleta ruleta);
    public Ruleta cierreRuleta(Ruleta ruleta);
    //public Integer guardar(Ruleta ruleta);
    public Integer guadrar(Ruleta ruleta);
    public List<Ruleta> listarRuletas();
}
