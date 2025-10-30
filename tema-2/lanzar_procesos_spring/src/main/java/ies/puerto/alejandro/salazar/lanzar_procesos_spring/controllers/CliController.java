package ies.puerto.alejandro.salazar.lanzar_procesos_spring.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl.LsofServiceImpl;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl.PsHeadServiceImpl;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl.TopServiceImpl;

@Service
public class CliController {
    
    @Autowired
    LsofServiceImpl comandoControllerLsof;

    @Autowired
    PsHeadServiceImpl comandoControllerPsHead;
    
    @Autowired
    TopServiceImpl comandoControllerTop;
    
    public void menuConsola() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Lanzador de Procesos (CLI) Linux/Windows ===\n" +
                "Comandos:\n" +
                "  lsof -i\n" +
                "  top\n" +
                "  ps aux | head\n");
        String linea = scanner.nextLine();
        String inicio = linea.toUpperCase().split("\s+")[0];
        switch (inicio) {
            case "TOP":
            comandoControllerTop.procesarLinea(linea);;
                break;
            case "LSOF":
            comandoControllerLsof.procesarLinea(linea);

                break;
            case "PS":
            comandoControllerPsHead.procesarLinea(linea);

                break;
            default:
                System.out.println("comando no valido");
                break;
        }
/* 
        if (linea.toUpperCase().startsWith("PS")) {
            comandoController.procesarLinea(linea);
        }else{
            comandoControllerLs.procesarLinea(linea);
        }
 */
        System.out.println("comando que pretendo lanzar: " + linea);
        scanner.close();
    }


}
