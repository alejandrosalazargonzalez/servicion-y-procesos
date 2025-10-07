package org.formacion.procesos.repository;

import org.springframework.stereotype.Repository;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Repository("baseDatosRepository")
public class BaseDatosRepository implements IFicheroRepository {

    @Override
    public String saludar() {
        return "Te estoy saludando desde el repositorio de bbdd";
    }
    
}
