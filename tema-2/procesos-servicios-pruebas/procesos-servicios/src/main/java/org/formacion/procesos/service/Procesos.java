package org.formacion.procesos.service;

import org.formacion.procesos.Interfaces.IFicheroComponent;
import org.formacion.procesos.service.interfaces.IProcesos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Service
public class Procesos implements IProcesos{

    @Autowired
    IFicheroComponent componentFichero;
    

    public void ejecutar() {
        System.out.println("Ejecutando lógica del proceso...");
        System.out.println(componentFichero.mensaje());
    }


    @Override
    public String saludar() {
        return "hola mundo";
    }

}