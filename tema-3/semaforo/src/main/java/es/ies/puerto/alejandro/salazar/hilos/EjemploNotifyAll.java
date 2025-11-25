package es.ies.puerto.alejandro.salazar.hilos;

class Cola {
    private boolean disponible = false;

    public synchronized void esperar() throws InterruptedException {
        while (!disponible) {
            System.out.println(Thread.currentThread().getName() + " está esperando...");
            wait();  // Hilo espera a que el recurso esté disponible
        }
        System.out.println(Thread.currentThread().getName() + " ha sido notificado.");
    }

    public synchronized void notificarTodos() {
        disponible = true;
        notifyAll();  // Despierta a todos los hilos que están esperando
        System.out.println("Todos los hilos han sido notificados.");
    }
}

public class EjemploNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        Cola cola = new Cola();

        // Crear varios hilos que están esperando
        Thread hilo1 = new Thread(() -> {
            try {
                cola.esperar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Hilo 1");

        Thread hilo2 = new Thread(() -> {
            try {
                cola.esperar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Hilo 2");

        Thread hilo3 = new Thread(() -> {
            try {
                cola.esperar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Hilo 3");

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // Simula algún procesamiento antes de notificar a todos
        Thread.sleep(2000);

        // Notificar a todos los hilos
        cola.notificarTodos();
    }
}