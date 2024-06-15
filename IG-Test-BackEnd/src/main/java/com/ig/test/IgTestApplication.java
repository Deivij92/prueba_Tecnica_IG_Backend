package com.ig.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IgTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IgTestApplication.class, args);
        System.out.println("Se ha inciado la aplicación IGTest");
    }

}
