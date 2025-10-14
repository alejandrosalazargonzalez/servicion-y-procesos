package org.formacion.procesos.controller;

import org.formacion.procesos.controller.abstractas.ComandoControllerAbstract;
import org.formacion.procesos.domain.ProcessType;
import org.springframework.stereotype.Component;

@Component
public class ComandoControllerPs extends ComandoControllerAbstract{

    public ComandoControllerPs(){
        this.setTipo(ProcessType.PS);
    }

    @Override
    public void imprimeMensaje(){
        System.out.println("llamado desde ControllerPs");
    }
}
