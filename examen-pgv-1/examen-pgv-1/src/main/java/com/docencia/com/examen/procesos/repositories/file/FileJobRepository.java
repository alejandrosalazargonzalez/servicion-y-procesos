package com.docencia.com.examen.procesos.repositories.file;

import java.net.URL;
import java.nio.file.*;

import org.springframework.stereotype.Repository;

import com.docencia.com.examen.procesos.repositories.interfaces.JobRepository;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
@Repository
public class FileJobRepository implements JobRepository {

    private String fileName = "logger.txt";
    private Path path;

    /**
     * constructor vacio de la clase
     */
    public FileJobRepository() {
        try {
            if (fileName.isEmpty() || fileName == null) {
                this.fileName = "logger.txt";
            }
            path = Paths.get("src/main/java/com/docencia/com/examen/procesos/resources/logger.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * constructor con el nombre del archivo
     * @param loggerFileName
     */
    public FileJobRepository(String loggerFileName) {
        try {
            if (loggerFileName.isEmpty() || loggerFileName == null) {
                fileName = "logger.txt";
            }
            this.fileName = loggerFileName;
            path = Paths.get("src/main/java/com/docencia/com/examen/procesos/resources/logger.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Path getPath() {
        return path;
    }

    /**
     * agrega contenido al documento
     */
    @Override
    public boolean add(String content) {

        try {

        } catch (Exception e) {
        }
        return false;
    }
}
