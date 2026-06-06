package com.donaton.necesidadesservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comuna;

    private String categoria;

    private Integer cantidadNecesaria;

    private String descripcion;

    private String prioridad;

    private String estado;
}