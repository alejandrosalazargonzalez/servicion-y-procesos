package es.ies.puerto.lanzar_hilos;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BatallaPokemon {
    public AtomicBoolean juegoTerminado = new AtomicBoolean(false);
    public AtomicInteger hpPikachu = new AtomicInteger(100);
    public AtomicInteger hpCharmander = new AtomicInteger(100);
    public String turno = "Pikachu";        // alternancia estricta
    public ReentrantLock m = new ReentrantLock();
    public Condition turnoCambio = m.newCondition();

    public void atacar (String atacante, AtomicInteger hpObjetivo){
        AtomicInteger dano = ThreadLocalRandom.current().nextInt(5, 21);
        hpObjetivo -= dano;
        System.out.println(atacante + " ataca con " + dano + " de daño. HP rival: " + hpObjetivo);

        if(hpObjetivo <= 0 && !juegoTerminado.get()){
            juegoTerminado = new AtomicBoolean(true);
            System.out.println(atacante + " ha ganado la batalla!");
        }
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 601));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    Runnable HiloPikachu = () -> {
        while (!juegoTerminado.get()){
            m.lock();
            try {
            while (!turno.equals("Pikachu") && !juegoTerminado.get()){
                    turnoCambio.await();
                }
                if( juegoTerminado.get()) break;
                atacar("Pikachu", hpCharmander);
                turno = "Charmander";
                turnoCambio.signalAll();;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally{
                m.unlock();;
            }
        }
    };

    Runnable HiloCharmander = () -> {
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
        Thread pikachu = new Thread(new BatallaPokemon().HiloPikachu);
        Thread charmander = new Thread(new BatallaPokemon().HiloCharmander);
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

