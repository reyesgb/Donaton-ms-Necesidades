package com.donaton.necesidadesservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void corsConfigurationSource_DebeConfigurarCorsCorrectamente() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/cualquier-ruta"); // Simulamos una ruta cualquiera

        // Act
        CorsConfigurationSource source = corsConfig.corsConfigurationSource();
        CorsConfiguration config = source.getCorsConfiguration(request);

        // Assert
        assertNotNull(config, "La configuración CORS no debería ser nula");

        // Verificamos que contenga los comodines que configuraste (usando OriginPatterns)
        assertTrue(config.getAllowedOriginPatterns().contains("*"), "Debe permitir todos los orígenes mediante patrón");
        assertTrue(config.getAllowedMethods().contains("*"), "Debe permitir todos los métodos");
        assertTrue(config.getAllowedHeaders().contains("*"), "Debe permitir todos los headers");

        // Verificamos las credenciales
        assertTrue(config.getAllowCredentials(), "Debe permitir credenciales");
    }
}