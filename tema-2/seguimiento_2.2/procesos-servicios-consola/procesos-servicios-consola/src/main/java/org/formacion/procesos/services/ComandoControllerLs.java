package org.formacion.procesos.services;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.services.abstractas.ComandoServicesAbstract;
import org.springframework.stereotype.Component;

@Component
public class ComandoControllerLs extends ComandoServicesAbstract{

    public ComandoControllerLs(){
        this.setTipo(ProcessType.LS);
    }
    
    @Override
    public void imprimeMensaje(){
        System.out.println("llamado desde ControllerLs");
    }

    @Override
    public boolean validar(String[] arrayComando) {
        if (!super.validarComando()) {
            return false;
        }
        String parametro = arrayComando[1];
        return true;
    }
}
