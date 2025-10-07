package org.formacion.procesos.service;

import org.formacion.procesos.component.IFicheroComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Service
public class Procesos {

    @Autowired
    IFicheroComponent componentFichero;
    

    public void ejecutar() {
        System.out.println("Ejecutando lógica del proceso...");
        System.out.println(componentFichero.mensaje());
    }

}