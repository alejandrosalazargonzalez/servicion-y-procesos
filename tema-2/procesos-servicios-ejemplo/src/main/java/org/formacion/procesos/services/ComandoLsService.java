package org.formacion.procesos.services;


import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServicesAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoLsService extends ComandoServicesAbstract{

    public ComandoLsService(){
        this.setTipo(ProcessType.LS);
        this.setExpresionRegular("^((-(la|l|a))|\s*)$");
    }
}
