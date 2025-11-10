package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class Cazahorrocruxes {

    AtomicBoolean encontrado = new AtomicBoolean(false);
    AtomicReference<String> ganador = new AtomicReference<>(null);

    public AtomicBoolean getEncontrado() {
        return encontrado;
    }

    public AtomicReference<String> getGanador() {
        return ganador;
    }

    Runnable buscador(String nombre, String ubicacion){
        return ()->{
            int tiempo = ThreadLocalRandom.current().nextInt(500, 2001);
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (!encontrado.get()){
            encontrado.set(true);
            ganador.set(nombre);
            System.out.println(nombre + " encontró un Horrocrux en " + ubicacion + ". ¡Búsqueda terminada!");
        }
        };
    }

    public void main(){
        Thread t1 = new Thread(buscador("Harry", "Bosque Prohibido"));
        Thread t2 = new Thread(buscador("Hermione", "Biblioteca Antigua"));
        Thread t3 = new Thread(buscador("Ron", "Mazmorras del castillo"));
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
