package org.formacion.procesos.hilos;

public class MiRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("soy un hilo desde Runnable");
    }
    
}
