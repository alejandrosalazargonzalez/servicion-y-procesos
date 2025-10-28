package ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.interfaces;

import java.nio.file.Path;

public interface JobRepository {

    public boolean add(String text);
    public Path getPath();
}
