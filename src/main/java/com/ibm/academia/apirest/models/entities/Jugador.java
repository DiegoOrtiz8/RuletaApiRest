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
@Table(name = "jugadores", schema = "casino")
public class Jugador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dinero", nullable = false)
    @Positive(message = "Debe de ser positivo")
    @Positive(message = "No debe de ser nulo")
    private Double dinero;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @OneToMany(mappedBy = "jugador")
    private List<Apuesta> apuestas;

    public Jugador(Integer id, Double dinero) {
        this.id = id;
        this.dinero = dinero;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", dinero=" + dinero +
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
