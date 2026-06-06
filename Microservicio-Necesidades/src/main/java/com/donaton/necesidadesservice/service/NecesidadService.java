package com.donaton.necesidadesservice.service;

import com.donaton.necesidadesservice.dto.NecesidadDTO;
import com.donaton.necesidadesservice.model.Necesidad;
import com.donaton.necesidadesservice.repository.NecesidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecesidadService {

    private final NecesidadRepository repository;

    public NecesidadService(NecesidadRepository repository) {
        this.repository = repository;
    }

    public Necesidad guardar(Necesidad necesidad) {

        necesidad.setEstado("ACTIVA");

        return repository.save(necesidad);
    }

    public List<Necesidad> obtenerActivas() {

        return repository.findByEstado("ACTIVA");
    }

    public Necesidad cambiarEstado(
            Long id,
            String estado
    ) {

        Necesidad necesidad =
                repository.findById(id)
                        .orElseThrow();

        necesidad.setEstado(estado);

        return repository.save(necesidad);
    }

    public Necesidad actualizar(
            Long id,
            NecesidadDTO dto
    ) {
        Necesidad necesidad =
                repository.findById(id)
                        .orElseThrow();

        necesidad.setComuna(dto.getComuna());
        necesidad.setCategoria(dto.getCategoria());
        necesidad.setCantidadNecesaria(
                dto.getCantidadNecesaria()
        );
        necesidad.setDescripcion(
                dto.getDescripcion()
        );

        return repository.save(necesidad);
    }

    public List<Necesidad> listar() {
        return repository.findAll();
    }
}
