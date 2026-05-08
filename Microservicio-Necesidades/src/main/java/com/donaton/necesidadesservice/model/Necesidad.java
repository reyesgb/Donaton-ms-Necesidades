package com.donaton.necesidadesservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "necesidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private int cantidadNecesaria;
    private String ubicacion;
}