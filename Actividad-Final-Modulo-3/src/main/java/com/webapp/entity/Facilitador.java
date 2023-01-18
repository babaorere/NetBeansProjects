package com.webapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facilitador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rut;
    private String nombre;
    private String email;
    private String telefono;
    private Float valorHora;
    private String banco;
    private String ctaBancaria;
    private LocalDate lastUpdate;

}
