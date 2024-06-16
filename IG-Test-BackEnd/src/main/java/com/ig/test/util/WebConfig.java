package com.ig.test.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Permite todos los orígenes, ajusta según tus necesidades
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite los métodos HTTP necesarios
                .allowCredentials(false)
                .maxAge(3600);  // Tiempo de vida de la configuración de CORS en segundos
    }
}
