package com.donaton.necesidadesservice.repository;

import com.donaton.necesidades.model.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NecesidadRepository extends JpaRepository<Necesidad, Long> {
}
