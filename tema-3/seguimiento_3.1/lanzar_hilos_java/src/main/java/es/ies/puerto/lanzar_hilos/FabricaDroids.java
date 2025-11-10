package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class FabricaDroids {
    private final LinkedBlockingQueue<String> ensamblados = new LinkedBlockingQueue<>();
    private final int N = 10;
    private final AtomicInteger activados = new AtomicInteger(0);

    public LinkedBlockingQueue<String> getEnsamblados() {
        return ensamblados;
    }

    public int getN() {
        return N;
    }

    public AtomicInteger getActivados() {
        return activados;
    }

    Runnable ensamblador = ()->{ 
        for(int i = 1; i<=N;i++){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100,301));
                String d = "Droid-" + i;
                System.out.println("Ensamblado " + d);
                ensamblados.put(d);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
                return;
            }
        }
    };
        
    Runnable activador = () -> {
        int cuenta = 0;
        while( cuenta < N){
            try {
                String d = ensamblados.take();
                System.out.println("Activado " + d);
                activados.incrementAndGet();
                cuenta++;
                Thread.sleep(ThreadLocalRandom.current().nextInt(50,151));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    };

    public void main(){
        Thread tE = new Thread(ensamblador);
        Thread tA = new Thread(activador);
        tE.start();
        tA.start();

        try {
            tE.join();
            tA.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
