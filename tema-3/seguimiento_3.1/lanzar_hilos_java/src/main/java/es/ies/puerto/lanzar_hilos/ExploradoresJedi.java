package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class ExploradoresJedi {

    private final AtomicBoolean pistaEncontrada = new AtomicBoolean(false);
    private final AtomicReference<String> ganador = new AtomicReference<>(null);

    public boolean isPistaEncontrada() {
        return pistaEncontrada.get();
    }

    public String getGanador() {
        return ganador.get();
    }

    private Runnable jedi(String nombre, String planeta) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(400, 1501));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!pistaEncontrada.get()) {
                synchronized (this) {
                    if (!pistaEncontrada.get()) {
                        pistaEncontrada.set(true);
                        ganador.set(nombre);
                        System.out.println(nombre + " halló una pista en " + planeta + ". Fin de búsqueda.");
                    }
                }
            }
        };
    }

    public void main() {
        Thread t1 = new Thread(jedi("Kenobi", "Tatooine"));
        Thread t2 = new Thread(jedi("Skywalker", "Dagobah"));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}