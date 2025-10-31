package ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.file;

import org.springframework.stereotype.Repository;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.interfaces.JobRepository;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  @author: alejandrosalazargonzalez
 *  @version: 1.0.0
 */
@Repository
public class FileJobRepository implements JobRepository{

    private String fileName;
    private Path path;

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    @Override
    public Path getPath() {
        return path;
    }

    /**
     * inicializa el path para guardar el resultado del comando
     */
    public FileJobRepository(){
        if (fileName == null) {
            fileName = "mis_procesos.txt";
        }
        URL resource = getClass().getClassLoader().getResource(fileName);
        path = Paths.get(resource.getPath());
    }
    

}
