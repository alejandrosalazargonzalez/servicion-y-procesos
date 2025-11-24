
package es.ies.puerto.alejandro.salazar.seguimiento3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * reto 3: enemigo epico, tiene menor probabilidad de aparecer
 * 
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class SpawnsMundoAbierto {

    static class SpawnTarea implements Runnable {
        @Override
        public void run() {
            String hilo = Thread.currentThread().getName();

            GameUtils.Zona zona = GameUtils.Zona.values()[(int) (Math.random() * GameUtils.Zona.values().length)];
            GameUtils.TipoEnemigo enemigoBase = GameUtils.TipoEnemigo
                    .values()[(int) (Math.random() * GameUtils.TipoEnemigo.values().length)];

            boolean esEpico = Math.random() < 0.10;
            String nombreEnemigo = (esEpico ? " JEFE DE MUNDO " : "") + enemigoBase;

            GameUtils.log(hilo, " SPAWN DETECTADO: " + nombreEnemigo + " en " + zona);
            if (esEpico) {
                GameUtils.log(hilo, "!!! ALERTA GENERAL: Ha aparecido un boss épico !!!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        GameUtils.log("MAIN", "Iniciando motor de mundo abierto...");

        pool.scheduleAtFixedRate(new SpawnTarea(), 0, 1, TimeUnit.SECONDS);

        Thread.sleep(5000);

        GameUtils.log("MAIN", "Apagando servidores...");
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
    }
}