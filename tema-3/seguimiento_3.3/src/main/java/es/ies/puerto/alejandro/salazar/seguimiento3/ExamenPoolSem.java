package es.ies.puerto.alejandro.salazar.seguimiento3;

import java.util.concurrent.*;

public class ExamenPoolSem {

    public static void main(String[] args) {
        // 1. Crear Pool de 10 hilos (capacidad de trabajo)
        ExecutorService pool = Executors.newFixedThreadPool(8);
        
        // 2. Crear Semáforo de 3 permisos (capacidad del recurso)
        Semaphore semaforo = new Semaphore(3);

        for (int i = 0; i < 15; i++) {
            pool.execute(() -> {
                try {
                    // --- INICIO ZONA CRÍTICA ---
                    semaforo.acquire(); // Solicita permiso
                    System.out.println("Usando recurso... " + Thread.currentThread().getName());
                    
                    Thread.sleep(2000); // Simula trabajo
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // IMPORTANTE: El release siempre en finally
                    System.out.println("Liberando recurso... " + Thread.currentThread().getName());
                    semaforo.release(); // Libera permiso
                    // --- FIN ZONA CRÍTICA ---
                }
            });
        }

        pool.shutdown();
    }
}
