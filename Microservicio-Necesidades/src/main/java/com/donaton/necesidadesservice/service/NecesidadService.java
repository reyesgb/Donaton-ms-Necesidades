package com.donaton.necesidadesservice.service;

import com.donaton.necesidadesservice.repository.NecesidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecesidadService {

    private final NecesidadRepository repository;

    public NecesidadService(NecesidadRepository repository) {
        this.repository = repository;
    }

    public com.donaton.necesidades.model.Necesidad guardar(com.donaton.necesidades.model.Necesidad necesidad) {
        return repository.save(necesidad);
    }

    public List<com.donaton.necesidades.model.Necesidad> listar() {
        return repository.findAll();
    }
}
