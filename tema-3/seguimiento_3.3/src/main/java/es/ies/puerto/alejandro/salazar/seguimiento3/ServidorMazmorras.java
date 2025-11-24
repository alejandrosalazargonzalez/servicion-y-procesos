package es.ies.puerto.alejandro.salazar.seguimiento3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * reto 1: mostrar tiempo de espera
 *
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class ServidorMazmorras {

    static class PeticionMazmorra implements Runnable {
        private final String nombreJugador;
        private final String mazmorra;
        private final long tiempoCreacion;

        public PeticionMazmorra(String nombreJugador, String mazmorra) {
            this.nombreJugador = nombreJugador;
            this.mazmorra = mazmorra;
            this.tiempoCreacion = System.currentTimeMillis();
        }

        @Override
        public void run() {
            String hilo = Thread.currentThread().getName();
            long tiempoInicio = System.currentTimeMillis();
            long tiempoEnCola = tiempoInicio - tiempoCreacion;

            GameUtils.log(hilo, "Recibido a " + nombreJugador + ". Tiempo en cola: " + tiempoEnCola + "ms");
            GameUtils.log(hilo, "Preparando instancia '" + mazmorra + "'...");

            try {
                Thread.sleep(1000 + (int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                GameUtils.log(hilo, "ERROR: Jugador desconectado.");
                Thread.currentThread().interrupt();
                return;
            }
            GameUtils.log(hilo, ">> Mazmorra '" + mazmorra + "' LISTA para " + nombreJugador + " 🎮");
        }
    }

    public static void main(String[] args) {
        ExecutorService gmBots = Executors.newFixedThreadPool(10);

        String[] jugadores = { "Link", "Zelda", "Geralt", "Yennefer", "Gandalf", "Frodo", "Aragorn", "Leia", "Luke",
                "Vader" };
        String[] mazmorras = { "Hyrule", "Torre", "Moria", "Estrella Muerte", "Nido" };

        GameUtils.log("MAIN", "--- Iniciando Servidor de Login ---");

        for (int i = 0; i < jugadores.length; i++) {
            gmBots.execute(new PeticionMazmorra(jugadores[i], mazmorras[i % mazmorras.length]));
        }

        gmBots.shutdown();
    }
}
