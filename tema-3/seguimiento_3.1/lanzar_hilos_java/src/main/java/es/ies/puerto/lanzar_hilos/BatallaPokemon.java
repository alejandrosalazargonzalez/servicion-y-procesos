package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class BatallaPokemon {
    static AtomicBoolean juegoTerminado = new AtomicBoolean(false);
    static final AtomicInteger hpPikachu = new AtomicInteger(100);
    static final AtomicInteger hpCharmander = new AtomicInteger(100);
    static String turno = "Pikachu";        // alternancia estricta
    static final ReentrantLock m = new ReentrantLock();
    static final Condition turnoCambio = m.newCondition();

    public static AtomicBoolean getJuegoTerminado() {
        return juegoTerminado;
    }

    public static AtomicInteger getHppikachu() {
        return hpPikachu;
    }

    public static AtomicInteger getHpcharmander() {
        return hpCharmander;
    }

    public static void atacar (String atacante, AtomicInteger hpObjetivo){
        int dano = ThreadLocalRandom.current().nextInt(5, 21);
        hpObjetivo.addAndGet(-dano);
        System.out.println(atacante + " ataca con " + dano + " de daño. HP rival: " + hpObjetivo);

        if(hpObjetivo.get() <= 0 && !juegoTerminado.get()){
            juegoTerminado = new AtomicBoolean(true);
            System.out.println(atacante + " ha ganado la batalla!");
        }
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    Runnable hiloPikachu = () -> {
        while (!juegoTerminado.get()){
            m.lock();
            try {
            while (!turno.equals("Pikachu") && !juegoTerminado.get()){
                    turnoCambio.await();
                }
                if( juegoTerminado.get()) break;
                atacar("Pikachu", hpCharmander);
                turno = "Charmander";
                turnoCambio.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally{
                m.unlock();
            }
        }
    };

    Runnable hiloCharmander = () -> {
        while(!juegoTerminado.get()){
            m.lock();
            try {
                while( !turno.equals("Charmander") && !juegoTerminado.get()){
                        turnoCambio.await();
                }
                if (juegoTerminado.get()) break;
                atacar("Charmander", hpPikachu);
                turno = "Pikachu";
                turnoCambio.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }finally{
                m.unlock();
            }
        }
    };

    public void main(){
        Thread pikachu = new Thread(new BatallaPokemon().hiloPikachu);
        Thread charmander = new Thread(new BatallaPokemon().hiloCharmander);
        pikachu.start();
        charmander.start();
        try {
            pikachu.join();
            charmander.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

