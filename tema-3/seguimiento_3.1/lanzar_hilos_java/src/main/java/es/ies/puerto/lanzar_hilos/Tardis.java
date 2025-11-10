package es.ies.puerto.lanzar_hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class Tardis {

    private final AtomicBoolean destinoAlcanzado = new AtomicBoolean(false);
    private final AtomicReference<String> eraGanadora = new AtomicReference<>(null);
    
    public boolean isDestinoAlcanzado() {
        return destinoAlcanzado.get();
    }

    public String getEraGanadora() {
        return eraGanadora.get();
    }

    private Runnable viaje(String era) {
        return () -> {
            int tiempo = ThreadLocalRandom.current().nextInt(500, 2001);
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!destinoAlcanzado.get()) {
                synchronized (this) {
                    if (!destinoAlcanzado.get()) {
                        destinoAlcanzado.set(true);
                        eraGanadora.set(era);
                        System.out.println("La TARDIS llegó primero a " + era);
                    }
                }
            }
        };
    }

    public void main() {
        String[] eras = {"Roma Antigua", "Futuro Lejano", "Era Victoriana", "Año 3000"};
        List<Thread> hilos = new ArrayList<>();

        for (String e : eras) {
            Thread t = new Thread(viaje(e));
            hilos.add(t);
            t.start();
        }

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}