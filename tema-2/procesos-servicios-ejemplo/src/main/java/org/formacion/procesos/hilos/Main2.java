package org.formacion.procesos.hilos;

public class Main2 {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        System.out.println("arrancamos el hilo");
        myThread.start();
        try {
            System.out.println("hilo dormido");
            myThread.sleep(5000);
            System.out.println("hilo reanudado");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("hilo interrumpido");
        }
    }
}
