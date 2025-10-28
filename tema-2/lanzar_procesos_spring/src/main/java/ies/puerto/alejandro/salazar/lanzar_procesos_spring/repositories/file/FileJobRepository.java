package ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.interfaces.JobRepository;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Repository
public class FileJobRepository implements JobRepository{

    private static Logger logger = LoggerFactory.getLogger(FileJobRepository.class);
    private String fileName;
    private Path path;

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    @Override
    public Path getPath() {
        return path;
    }

    public FileJobRepository(){
        if (fileName == null) {
            fileName = "mis_procesos.txt";
        }
        URL resource = getClass().getClassLoader().getResource(fileName);
        path = Paths.get(resource.getPath());
    }

    @Override
    public boolean add(String text) {
        try {
            Files.write(path,text.getBytes(),StandardOpenOption.APPEND);
            return true;
        } catch (Exception e) {
            logger.error("Se ha prodicido un error");
        }
        return false;
    }
}
