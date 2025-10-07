package org.formacion.procesos.component;

import org.formacion.procesos.repository.IFicheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Component
public class FicheroComponent implements IFicheroComponent {

    @Autowired
    @Qualifier("ficheroRepository")
    IFicheroRepository ficheroRepository;

    @Autowired
    @Qualifier("baseDatosRepository")
    IFicheroRepository ficheroRepositorybbdd;
    
    @Override
    public String mensaje() {
        return ficheroRepository.saludar() + " " + ficheroRepositorybbdd.saludar();
    }

}
