package ies.puerto.alejandro.salazar.lanzar_procesos_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.controllers.CliController;

/**
 *  @author: alejandrosalazargonzalez
 *  @version: 1.0.0
 */
@SpringBootApplication
public class ProcCliApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProcCliApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(CliController procesos) {
        return args -> {
            System.out.println("Iniciando proceso al arrancar la aplicación...");

            procesos.menuConsola();

            System.out.println("Proceso finalizado.");
        };
    }
}
