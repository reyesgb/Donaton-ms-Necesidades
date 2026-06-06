package com.donaton.necesidadesservice.controller;

import com.donaton.necesidadesservice.dto.NecesidadDTO;
import com.donaton.necesidadesservice.model.Necesidad;
import com.donaton.necesidadesservice.service.NecesidadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/necesidades")
@CrossOrigin(origins = "*")
public class NecesidadController {

    private final NecesidadService service;

    public NecesidadController(NecesidadService service) {
        this.service = service;
    }

    @PostMapping
    public Necesidad crear(@Valid @RequestBody NecesidadDTO dto) {

        Necesidad n = new Necesidad();

        n.setComuna(dto.getComuna());
        n.setCategoria(dto.getCategoria());
        n.setCantidadNecesaria(dto.getCantidadNecesaria());
        n.setDescripcion(dto.getDescripcion());

        // Estado inicial
        n.setEstado("PENDIENTE");

        return service.guardar(n);
    }

    @PutMapping("/{id}/estado")
    public Necesidad cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado
    ) {
        return service.cambiarEstado(id, estado);
    }

    @GetMapping("/activas")
    public List<Necesidad> listarActivas() {
        return service.obtenerActivas();
    }

    @PutMapping("/{id}")
    public Necesidad actualizar(
            @PathVariable Long id,
            @RequestBody NecesidadDTO dto
    ) {
        return service.actualizar(id, dto);
    }

    @GetMapping
    public List<Necesidad> listar() {
        return service.listar();
    }
}