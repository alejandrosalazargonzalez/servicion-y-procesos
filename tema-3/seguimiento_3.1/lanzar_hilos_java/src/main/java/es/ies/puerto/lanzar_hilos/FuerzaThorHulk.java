package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class FuerzaThorHulk {

    private final static int durationMS = 5000;
    private final AtomicBoolean tiempoTerminado = new AtomicBoolean(false);
    private final AtomicInteger totalThor = new AtomicInteger(0);
    private final AtomicInteger totalHulk = new AtomicInteger(0);

    public boolean isTiempoTerminado() {
        return tiempoTerminado.get();
    }

    public int getTotalThor() {
        return totalThor.get();
    }

    public int getTotalHulk() {
        return totalHulk.get();
    }

    private final Runnable temporizador = () -> {
        try {
            Thread.sleep(durationMS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        tiempoTerminado.set(true);
        System.out.println("¡Tiempo!");
    };

    private final Runnable thor = () -> {
        while (!tiempoTerminado.get()) {
            int peso = ThreadLocalRandom.current().nextInt(5, 21);
            totalThor.addAndGet(peso);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 121));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    };

    private final Runnable hulk = () -> {
        while (!tiempoTerminado.get()) {
            int peso = ThreadLocalRandom.current().nextInt(5, 21);
            totalHulk.addAndGet(peso);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 121));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    };

    public void main() {
        Thread t0 = new Thread(temporizador);
        Thread t1 = new Thread(thor);
        Thread t2 = new Thread(hulk);

        t0.start();
        t1.start();
        t2.start();

        try {
            t0.join();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int thorInt = totalThor.get();
        int hulkInt = totalHulk.get();

        if (thorInt > hulkInt) {
            System.out.println("Thor gana con " + thorInt + " vs " + hulkInt);
        } else if (hulkInt > thorInt) {
            System.out.println("Hulk gana con " + hulkInt + " vs " + thorInt);
        } else {
            System.out.println("Empate: " + thorInt);
        }
    }
}