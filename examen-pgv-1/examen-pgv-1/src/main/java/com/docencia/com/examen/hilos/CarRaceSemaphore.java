package com.docencia.com.examen.hilos;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class CarRaceSemaphore implements Runnable {

    private String name;
    private int goal;
    private int distance = 0;
    private static final AtomicBoolean winnerDeclared = new AtomicBoolean(false);
    private static final Semaphore semaphore = new Semaphore(1,true);

    /**
     * constructor de la clase
     * 
     * @param name del coche
     * @param goal del coche
     */
    public CarRaceSemaphore(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }

    @Override
    public void run() {
        while (!winnerDeclared.get()) {
            try {
                semaphore.acquire();
                System.out.println(name + " entra al semaforo");
                Random random = new Random();
                int recorrido = random.nextInt(10) + 1;
                distance += recorrido;
                System.out.println(name + " ha avanzado " + recorrido + " metros para la meta de " + goal);

                if (distance >= goal) {
                    winnerDeclared.set(true);
                    System.out.println(name + " ha llegado a la meta. Ha recorrido " + goal + " metros");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(name + " sale del semaforo");
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("    🏁 CARRERA DE COCHES 🏁");
        System.out.println("   Rayo-McQueen vs Mate");
        System.out.println("═══════════════════════════════════════");

        int raceGoal = 100;

        Thread rayoMcQueen = new Thread(new CarRaceSemaphore("Rayo-McQueen", raceGoal));
        Thread mate = new Thread(new CarRaceSemaphore("Mate", raceGoal));

        rayoMcQueen.start();
        mate.start();

        try {
            rayoMcQueen.join();
            mate.join();

            System.out.println("\n═══════════════════════════════════════");
            System.out.println("        🏁 CARRERA TERMINADA 🏁");
            System.out.println("═══════════════════════════════════════");

        } catch (InterruptedException e) {
            System.out.println("La carrera fue interrumpida!");
        }
    }

}
