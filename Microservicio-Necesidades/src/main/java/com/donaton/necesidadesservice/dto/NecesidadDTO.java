package com.donaton.necesidadesservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NecesidadDTO {

    @NotBlank
    private String comuna;

    @NotBlank
    private String categoria;

    @Min(1)
    private Integer cantidadNecesaria;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String prioridad;
}