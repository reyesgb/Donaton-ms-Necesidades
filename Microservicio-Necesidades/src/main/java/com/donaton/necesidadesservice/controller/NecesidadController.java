package com.donaton.necesidadesservice.controller;

import com.donaton.necesidadesservice.dto.NecesidadDTO;
import com.donaton.necesidadesservice.service.NecesidadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.donaton.necesidadesservice.model.Necesidad;


@RestController
@RequestMapping("/necesidades")
public class NecesidadController {

    private final NecesidadService service;

    public NecesidadController(NecesidadService service) {
        this.service = service;
    }

    @PostMapping
    public Necesidad crear(@Valid @RequestBody NecesidadDTO dto) {

        Necesidad n = new Necesidad();

        n.setDescripcion(dto.getDescripcion());
        n.setCantidadNecesaria(dto.getCantidadNecesaria());
        n.setUbicacion(dto.getUbicacion());

        return service.guardar(n);
    }

    @GetMapping
    public List<Necesidad> listar() {
        return service.listar();
    }
}