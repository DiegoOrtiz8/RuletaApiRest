package com.ibm.academia.apirest.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "apuestas", schema = "casino")
public class Apuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dinero",nullable = false)
    @Positive(message = "No puede ser negativo")
    private Double dinero;

    @Column(name = "apuesta_realizada",nullable = false)
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    private String apuestaRealizada;

    @Column(name = "resultado",nullable = false)
    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    private String resultado;

    @Column(name = "estado",nullable = false)
    private Integer estado;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id",foreignKey = @ForeignKey(name = "FK_JUGADOR_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "apuestas"})
    private Jugador jugador;

    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleta_id",foreignKey = @ForeignKey(name = "FK_RULETA_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "apuestas"})
    private Ruleta ruleta;

    public Apuesta(Integer id, Double dinero, Integer estado) {
        this.id = id;
        this.dinero = dinero;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Apuesta{" +
                "id=" + id +
                ", dinero=" + dinero +
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
