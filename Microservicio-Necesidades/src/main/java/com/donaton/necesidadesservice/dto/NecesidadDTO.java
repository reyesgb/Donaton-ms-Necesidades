package com.donaton.necesidadesservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NecesidadDTO {

    @NotBlank
    private String descripcion;
    @Min(1)
    private int cantidadNecesaria;
    @NotBlank
    private String ubicacion;
}