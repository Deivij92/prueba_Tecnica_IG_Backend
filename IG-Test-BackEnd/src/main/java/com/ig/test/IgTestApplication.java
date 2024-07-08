package com.ig.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(exclude = {
        WebFluxAutoConfiguration.class,
        ReactiveWebServerFactoryAutoConfiguration.class
})
public class IgTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IgTestApplication.class, args);
        System.out.println("Se ha inciado la aplicación IGTest");
    }

}
