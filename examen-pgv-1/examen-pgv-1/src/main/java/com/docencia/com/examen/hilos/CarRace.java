package com.docencia.com.examen.hilos;

import java.util.Random;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class CarRace implements Runnable {
    private String name;
    private int distance = 0;
    private int goal;
    private static volatile boolean winnerDeclared = false;
    /**
     * Constructor de CarRace
     * @param name del coche
     * @param goal del coche
     */
    public CarRace(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distance < goal && !winnerDeclared) {
            int recorrido = random.nextInt(10)+1;
            distance += recorrido;
            System.out.println(name + " ha avanzado " + recorrido + " metros para la meta de " + goal );

            if (distance>=goal) {
                winnerDeclared = true;
                System.out.println(name + " ha llegado a la meta. Ha recorrido " + goal + " metros");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread mcQueen = new Thread(new CarRace("mcQuenn", 90));
        Thread jacksonStone = new Thread(new CarRace("jacksonStone", 90));

        mcQueen.start();
        jacksonStone.start();
    }
}
