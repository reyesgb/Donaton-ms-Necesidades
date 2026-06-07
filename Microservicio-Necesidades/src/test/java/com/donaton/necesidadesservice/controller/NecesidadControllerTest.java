package com.donaton.necesidadesservice.controller;

import com.donaton.necesidadesservice.dto.NecesidadDTO;
import com.donaton.necesidadesservice.model.Necesidad;
import com.donaton.necesidadesservice.service.NecesidadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NecesidadController.class)
class NecesidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NecesidadService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void crear_CuandoDtoEsValido_DebeRetornar200YNecesidadGuardada() throws Exception {
        // Arrange
        NecesidadDTO dto = new NecesidadDTO();
        dto.setComuna("Santiago");
        dto.setCategoria("Alimentos");
        dto.setCantidadNecesaria(50);
        dto.setDescripcion("Cajas de mercadería");
        dto.setPrioridad("ALTA"); // Solución a la validación @NotNull

        Necesidad necesidadGuardada = new Necesidad();
        necesidadGuardada.setId(1L);
        necesidadGuardada.setComuna("Santiago");
        necesidadGuardada.setEstado("PENDIENTE");
        necesidadGuardada.setPrioridad("ALTA");

        when(service.guardar(any(Necesidad.class))).thenReturn(necesidadGuardada);

        // Act & Assert
        mockMvc.perform(post("/necesidades")
                        .with(csrf()) // Necesario si CSRF está habilitado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.comuna").value("Santiago"));

        verify(service, times(1)).guardar(any(Necesidad.class));
    }

    @Test
    @WithMockUser
    void cambiarEstado_DebeRetornar200YNecesidadModificada() throws Exception {
        // Arrange
        Long id = 1L;
        String nuevoEstado = "APROBADA";
        Necesidad necesidadModificada = new Necesidad();
        necesidadModificada.setId(id);
        necesidadModificada.setEstado(nuevoEstado);

        when(service.cambiarEstado(eq(id), eq(nuevoEstado))).thenReturn(necesidadModificada);

        // Act & Assert
        mockMvc.perform(put("/necesidades/{id}/estado", id)
                        .with(csrf())
                        .param("estado", nuevoEstado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.estado").value(nuevoEstado));

        verify(service, times(1)).cambiarEstado(id, nuevoEstado);
    }

    @Test
    @WithMockUser
    void listarActivas_DebeRetornarListaDeNecesidadesActivas() throws Exception {
        // Arrange
        Necesidad n1 = new Necesidad(); n1.setId(1L); n1.setEstado("ACTIVA");
        Necesidad n2 = new Necesidad(); n2.setId(2L); n2.setEstado("ACTIVA");
        List<Necesidad> lista = Arrays.asList(n1, n2);

        when(service.obtenerActivas()).thenReturn(lista);

        // Act & Assert
        mockMvc.perform(get("/necesidades/activas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(service, times(1)).obtenerActivas();
    }

    @Test
    @WithMockUser
    void actualizar_DebeLlamarAlServicioYRetornarNecesidadActualizada() throws Exception {
        // Arrange
        Long id = 1L;
        NecesidadDTO dto = new NecesidadDTO();
        dto.setComuna("Maipú");
        dto.setPrioridad("MEDIA"); // Agregado también aquí por seguridad

        Necesidad necesidadActualizada = new Necesidad();
        necesidadActualizada.setId(id);
        necesidadActualizada.setComuna("Maipú");
        necesidadActualizada.setPrioridad("MEDIA");

        when(service.actualizar(eq(id), any(NecesidadDTO.class))).thenReturn(necesidadActualizada);

        // Act & Assert
        mockMvc.perform(put("/necesidades/{id}", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.comuna").value("Maipú"));

        verify(service, times(1)).actualizar(eq(id), any(NecesidadDTO.class));
    }

    @Test
    @WithMockUser
    void listar_DebeRetornarTodasLasNecesidades() throws Exception {
        // Arrange
        Necesidad n1 = new Necesidad();
        List<Necesidad> lista = Arrays.asList(n1);

        when(service.listar()).thenReturn(lista);

        // Act & Assert
        mockMvc.perform(get("/necesidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service, times(1)).listar();
    }
}