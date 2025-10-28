package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services;


import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

@Component
public class ComandoPsService extends CommandService{

    public ComandoPsService(){
        this.setTipo(Job.PS);
        this.setExpresionRegular("^((-(xa|x|a|aux))|\s*)$");
    }

}
