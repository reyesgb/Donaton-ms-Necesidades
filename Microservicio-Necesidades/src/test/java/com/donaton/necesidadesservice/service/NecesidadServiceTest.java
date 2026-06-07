package com.donaton.necesidadesservice.service;

import com.donaton.necesidadesservice.dto.NecesidadDTO;
import com.donaton.necesidadesservice.model.Necesidad;
import com.donaton.necesidadesservice.repository.NecesidadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NecesidadServiceTest {

    @Mock
    private NecesidadRepository repository;

    @InjectMocks
    private NecesidadService service;

    @Test
    void guardar_DebeAsignarEstadoActivaYGuardar() {
        // Arrange (Preparar)
        Necesidad necesidad = new Necesidad();
        necesidad.setDescripcion("Ropa de invierno");

        when(repository.save(any(Necesidad.class))).thenReturn(necesidad);

        // Act (Actuar)
        Necesidad resultado = service.guardar(necesidad);

        // Assert (Afirmar)
        assertEquals("ACTIVA", resultado.getEstado());
        verify(repository, times(1)).save(necesidad);
    }

    @Test
    void obtenerActivas_DebeRetornarListaDeNecesidadesActivas() {
        // Arrange
        Necesidad n1 = new Necesidad(); n1.setEstado("ACTIVA");
        Necesidad n2 = new Necesidad(); n2.setEstado("ACTIVA");
        List<Necesidad> esperadas = Arrays.asList(n1, n2);

        when(repository.findByEstado("ACTIVA")).thenReturn(esperadas);

        // Act
        List<Necesidad> resultado = service.obtenerActivas();

        // Assert
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findByEstado("ACTIVA");
    }

    @Test
    void cambiarEstado_CuandoExiste_DebeActualizarEstadoYGuardar() {
        // Arrange
        Long id = 1L;
        String nuevoEstado = "CERRADA";
        Necesidad necesidadExistente = new Necesidad();
        necesidadExistente.setEstado("ACTIVA");

        when(repository.findById(id)).thenReturn(Optional.of(necesidadExistente));
        when(repository.save(any(Necesidad.class))).thenReturn(necesidadExistente);

        // Act
        Necesidad resultado = service.cambiarEstado(id, nuevoEstado);

        // Assert
        assertEquals(nuevoEstado, resultado.getEstado());
        verify(repository, times(1)).save(necesidadExistente);
    }

    @Test
    void cambiarEstado_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            service.cambiarEstado(id, "CERRADA");
        });
        verify(repository, never()).save(any(Necesidad.class));
    }

    @Test
    void actualizar_CuandoExiste_DebeActualizarCamposYGuardar() {
        // Arrange
        Long id = 1L;
        Necesidad necesidadExistente = new Necesidad();

        NecesidadDTO dto = new NecesidadDTO();
        dto.setComuna("Providencia");
        dto.setCategoria("Alimentos");
        dto.setCantidadNecesaria(10);
        dto.setDescripcion("Latas de atún");

        when(repository.findById(id)).thenReturn(Optional.of(necesidadExistente));
        when(repository.save(any(Necesidad.class))).thenReturn(necesidadExistente);

        // Act
        Necesidad resultado = service.actualizar(id, dto);

        // Assert
        assertEquals("Providencia", resultado.getComuna());
        assertEquals("Alimentos", resultado.getCategoria());
        assertEquals(10, resultado.getCantidadNecesaria());
        assertEquals("Latas de atún", resultado.getDescripcion());
        verify(repository, times(1)).save(necesidadExistente);
    }

    @Test
    void actualizar_CuandoNoExiste_DebeLanzarExcepcion() {
        // Arrange
        Long id = 1L;
        NecesidadDTO dto = new NecesidadDTO();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> {
            service.actualizar(id, dto);
        });
        verify(repository, never()).save(any(Necesidad.class));
    }

    @Test
    void listar_DebeRetornarTodasLasNecesidades() {
        // Arrange
        List<Necesidad> esperadas = Arrays.asList(new Necesidad(), new Necesidad());
        when(repository.findAll()).thenReturn(esperadas);

        // Act
        List<Necesidad> resultado = service.listar();

        // Assert
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }
}