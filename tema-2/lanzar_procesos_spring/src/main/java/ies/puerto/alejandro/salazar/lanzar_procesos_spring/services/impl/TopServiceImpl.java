package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl;

import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

/**
 *  @author: alejandrosalazargonzalez
 *  @version: 1.0.0
 */
@Component
public class TopServiceImpl extends CommandService{

    /**
     * inicializa los valores para este comando
     */
    public TopServiceImpl(){
        this.setTipo(Job.TOP);
        this.setExpresionRegular("^((-(bn1))|\s*)$");
    }
}
