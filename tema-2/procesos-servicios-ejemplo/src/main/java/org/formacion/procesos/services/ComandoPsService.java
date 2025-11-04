package org.formacion.procesos.services;


import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServicesAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoPsService extends ComandoServicesAbstract{

    public ComandoPsService(){
        this.setTipo(ProcessType.PS);
        this.setExpresionRegular("^((-(xa|x|a|aux))|\s*)$");
    }

}
