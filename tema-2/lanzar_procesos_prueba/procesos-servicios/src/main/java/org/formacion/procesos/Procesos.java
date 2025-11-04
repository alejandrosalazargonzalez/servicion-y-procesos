package org.formacion.procesos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Component
public class Procesos {

    /**
     * crea un fichero donde se guarda el registro de los procesos de java
     * @throws IOException 
     * @throws InterruptedException
     */
    public void procesosEnFichero(){
            ProcessBuilder pb = new ProcessBuilder("sh", "-c", "ps aux | grep java");
            pb.redirectOutput(new File("mis_procesos.txt"));
            Process proceso;
            try {
                proceso = pb.start();
                proceso.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
    }

    /**
     * cuenta las lineas y si son mayor a 3 imprime un aviso en consola
     * @throws IOException
     */
    public void contarLineas(){
            int lineCount = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("mis_procesos.txt"))) {
                while (br.readLine() != null) {
                    lineCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Número de procesos Java: " + lineCount);
            if (lineCount > 3) {
                System.out.println("¡Cuidado, muchos procesos de Java activos!");
            }
    }

}