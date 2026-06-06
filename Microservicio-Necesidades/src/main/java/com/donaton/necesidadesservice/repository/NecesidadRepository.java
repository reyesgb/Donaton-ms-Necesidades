package com.donaton.necesidadesservice.repository;

import com.donaton.necesidadesservice.model.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NecesidadRepository extends JpaRepository<Necesidad, Long> {

    List<Necesidad> findByEstado(String estado);

}