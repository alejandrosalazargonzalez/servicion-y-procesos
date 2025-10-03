package org.formacion.procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;

/**
 * @author alejandrosalazargonzalez
 * @version 1.0.0
 */
@Component
public class Procesos {

    /**
     * imprime en pantalla un sout
     */
    public void ejecutar() {
        System.out.println("Ejecutando lógica del proceso...");
    }

    /**
     * crea un fichero donde se guarda el registro de los procesos de java
     */
    public void procesosEnFichero(){
            String[] cmd1 = {"/bin/sh", "-c", "ps aux | grep java > mis_procesos.txt"};
            try {
                Process process1 = Runtime.getRuntime().exec(cmd1);
                process1.waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * cuenta las lineas y si son mayor a 3 imprime un aviso en consola
     */
    public void contarLineas(){
        try {
            String[] contarLineasComando = {"/bin/sh", "-c", "wc -l mis_procesos.txt"};
            Process procesoContar = Runtime.getRuntime().exec(contarLineasComando);

            BufferedReader reader = new BufferedReader(new InputStreamReader(procesoContar.getInputStream()));
            String line;
            int lineCount = 0;
            if ((line = reader.readLine()) != null) {
                System.out.println("Procesos Java: " + line.trim());
            
                try {
                    lineCount = Integer.parseInt(line.trim().split("\\s+")[0]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            procesoContar.waitFor();
            if (lineCount > 3) {
                System.out.println("¡Cuidado, muchos procesos de Java activos!");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}