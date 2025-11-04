package org.formacion.procesos.hilos;

public class MyThread extends Thread {
    @Override
    public void run(){
        System.out.println("soy un hilo desde MyThread");
    }


}