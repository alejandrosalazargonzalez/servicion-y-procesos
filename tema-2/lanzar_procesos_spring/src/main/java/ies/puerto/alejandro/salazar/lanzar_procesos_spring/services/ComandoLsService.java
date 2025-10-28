package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services;


import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

@Component
public class ComandoLsService extends CommandService{

    public ComandoLsService(){
        this.setTipo(Job.LS);
        this.setExpresionRegular("^((-(la|l|a))|\s*)$");
    }
}
