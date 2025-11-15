package com.docencia.semaforo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SemaforoMejorado implements Runnable {

    private final String color;
    private static final AtomicReference<String> colorSiguiente = new AtomicReference<>("rojo");
    private static final Semaphore semaphore = new Semaphore(1);
    private static final AtomicBoolean encedido = new AtomicBoolean(true);
    
    public SemaforoMejorado(String color){
        this.color=color;
    }

    @Override
    public void run() {
        while (encedido.get()) {
            try {
                semaphore.acquire();
                if (!colorSiguiente.get().equals(color)) {
                    continue;
                }
                System.out.println(color);
                switch (color.toLowerCase()) {
                    case "ambar":
                        Thread.sleep(1000);
                        colorSiguiente.set("rojo");
                        break;
                    case "rojo":
                        Thread.sleep(3000);
                        colorSiguiente.set("verde");
                        break;
                    case "verde":
                        Thread.sleep(3000);
                        colorSiguiente.set("ambar");
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally{
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        Thread rojo = new Thread(new SemaforoMejorado("rojo"));
        Thread verde = new Thread(new SemaforoMejorado("verde"));
        Thread ambar = new Thread(new SemaforoMejorado("ambar"));

        rojo.start();
        verde.start();
        ambar.start();

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
