package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl;


import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

@Component
public class PsHeadServiceImpl extends CommandService{

    public PsHeadServiceImpl(){
        this.setTipo(Job.PS);
        this.setExpresionRegular("^(aux\\s+\\|\\s+head\s*)$");
    }

}
