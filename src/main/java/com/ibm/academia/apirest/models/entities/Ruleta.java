package com.ibm.academia.apirest.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ruletas", schema = "casino")
public class Ruleta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estado")
    //@Positive(message = "Debe de ser positivo")
    private Integer estado;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @OneToMany(mappedBy = "ruleta")
    private List<Apuesta> apuestas;

    public Ruleta(Integer id, Integer estado) {
        this.id = id;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ruleta{" +
                "id=" + id +
                ", estado=" + estado +
                '}';
    }

    @PrePersist
    private void antesPersistir(){
        this.fechaAlta = new Date();
    }

    @PreUpdate
    private void antesActualizar(){
        this.fechaModificacion = new Date();
    }
}
