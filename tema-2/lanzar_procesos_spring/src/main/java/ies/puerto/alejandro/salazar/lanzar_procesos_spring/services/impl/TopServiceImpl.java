package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl;

import org.springframework.stereotype.Component;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

@Component
public class TopServiceImpl extends CommandService{

    public TopServiceImpl(){
        this.setTipo(Job.TOP);
        
    }
}
