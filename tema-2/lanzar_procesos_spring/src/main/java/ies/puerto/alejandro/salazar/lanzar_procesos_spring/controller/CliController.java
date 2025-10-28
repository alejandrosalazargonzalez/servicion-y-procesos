package ies.puerto.alejandro.salazar.lanzar_procesos_spring.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.ComandoLsService;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.ComandoPsService;

@Service
public class CliController {
    
    @Autowired
    ComandoLsService comandoControllerLs;

    @Autowired
    ComandoPsService comandoController;
    
    public void menuConsola() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Lanzador de Procesos (CLI) Linux/Windows ===\n" +
                "Comandos:\n" +
                "  run PING host=8.8.8.8 count=4 timeoutMs=15000\n" +
                "  run LIST_DIR path=.\n" +
                "  run HASH_SHA256 file=README.md\n" +
                "  help | os | exit\n");
        String linea = scanner.nextLine();
        if (linea.toUpperCase().startsWith("PS")) {
            comandoController.procesarLinea(linea);
        }else{
            comandoControllerLs.procesarLinea(linea);
        }
        System.out.println("comando que pretendo lanzar: " + linea);
        scanner.close();
    }


}
