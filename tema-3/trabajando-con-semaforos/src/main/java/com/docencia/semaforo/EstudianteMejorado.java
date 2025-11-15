package com.docencia.semaforo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class EstudianteMejorado implements Runnable{

    private String nombre;
    private static final Semaphore semaphore = new Semaphore(4);

    public EstudianteMejorado(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            int permisos = semaphore.availablePermits()+1;
            System.out.println(nombre+" ha comenzado a utilizar el equipo " + permisos);
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 5001));
            System.out.println(nombre+" ha finalizado con el equipo " + permisos);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        Thread alumno1 = new Thread(new EstudianteMejorado("estudiante 1"));
        Thread alumno2 = new Thread(new EstudianteMejorado("estudiante 2"));
        Thread alumno3 = new Thread(new EstudianteMejorado("estudiante 3"));
        Thread alumno4 = new Thread(new EstudianteMejorado("estudiante 4"));
        Thread alumno5 = new Thread(new EstudianteMejorado("estudiante 5"));
        Thread alumno6 = new Thread(new EstudianteMejorado("estudiante 6"));

        alumno1.start();
        alumno2.start();
        alumno3.start();
        alumno4.start();
        alumno5.start();
        alumno6.start();

        try {
            alumno1.join();
            alumno2.join();
            alumno3.join();
            alumno4.join();
            alumno5.join();
            alumno6.join();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}
