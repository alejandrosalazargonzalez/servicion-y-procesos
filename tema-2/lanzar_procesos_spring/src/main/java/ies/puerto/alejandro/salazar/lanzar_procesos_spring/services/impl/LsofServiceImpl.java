package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl;


import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

@Component
public class LsofServiceImpl extends CommandService{

    public LsofServiceImpl(){
        this.setTipo(Job.LSOF);
        this.setExpresionRegular("^((-(i))|\s*)$");
    }
}
