package com.docencia.semaforo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Semaforo implements Runnable {

    private final String color;
    private static final Semaphore semaphore = new Semaphore(1);
    private static final AtomicBoolean encedido = new AtomicBoolean(true);
    public Semaforo(String color){
        this.color=color;
    }

    @Override
    public void run() {
        while (encedido.get()) {
            try {
                semaphore.acquire();
                System.out.println(color);
                switch (color.toLowerCase()) {
                    case "ambar":
                        Thread.sleep(1000);
                        break;
                    case "rojo","verde":
                        Thread.sleep(3000);
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }finally{
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        Thread rojo = new Thread(new Semaforo("rojo"));
        Thread verde = new Thread(new Semaforo("verde"));
        Thread ambar = new Thread(new Semaforo("ambar"));

        verde.start();
        ambar.start();
        rojo.start();

        try {
            Thread.sleep(20000);
            encedido.set(false);
            verde.join();
            rojo.join();
            ambar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

}
