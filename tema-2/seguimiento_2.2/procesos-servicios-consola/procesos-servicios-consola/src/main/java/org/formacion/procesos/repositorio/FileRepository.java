package org.formacion.procesos.repositorio;

import org.formacion.procesos.repositorio.interfaces.CrudInterface;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository implements CrudInterface{

    String fileName;

    @Override
    public boolean add(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

}
