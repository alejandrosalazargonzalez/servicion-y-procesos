package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServicesAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoControllerPs extends ComandoServicesAbstract{

    public ComandoControllerPs(){
        this.setTipo(ProcessType.PS);
    }

    @Override
    public void imprimeMensaje(){
        System.out.println("llamado desde ControllerPs");
    }

    @Override
    public boolean validar(String[] arrayComando) {
        if (!super.validarComando()) {
            return false;
        }
        return true;
    }
}
