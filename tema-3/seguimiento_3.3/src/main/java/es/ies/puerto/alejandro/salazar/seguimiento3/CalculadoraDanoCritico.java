
package es.ies.puerto.alejandro.salazar.seguimiento3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *   @author: alejandrosalazargonzalez
 *   @version: 1.0.0
 */
public class CalculadoraDanoCritico {

    static class Ataque {
        final String atacante;
        final int danoBase;
        final double probCritico;
        final double multiplicadorCritico;

        Ataque(String atacante, int danoBase, double probCritico, double multiplicadorCritico) {
            this.atacante = atacante;
            this.danoBase = danoBase;
            this.probCritico = probCritico;
            this.multiplicadorCritico = multiplicadorCritico;
        }
    }

    static class TareaCalcularDano implements Callable<Integer> {
        private final Ataque ataque;

        TareaCalcularDano(Ataque ataque) {
            this.ataque = ataque;
        }

        @Override
        public Integer call() throws Exception {
            String hilo = Thread.currentThread().getName();

            boolean esCritico = Math.random() < ataque.probCritico;
            double multiplicador = esCritico ? ataque.multiplicadorCritico : 1.0;

            Thread.sleep(500 + (int)(Math.random() * 500));

            int danoFinal = (int) (ataque.danoBase * multiplicador);
            System.out.println("[" + hilo + "] " + ataque.atacante +
                    (esCritico ? " ¡CRÍTICO!" : " golpe normal") +
                    " -> daño: " + danoFinal);

            return danoFinal;
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future<Integer>> futuros = new ArrayList<>();

        Ataque[] ataques = {
                new Ataque("Mago del Fuego", 120, 0.90, 2.5),
                new Ataque("Guerrero", 150, 0.85, 2.0),
                new Ataque("Pícaro", 90, 0.70, 3.0),
                new Ataque("Arquera Élfica", 110, 0.95, 2.2),
                new Ataque("Invocador", 80, 0.80, 2.8),
                new Ataque("Paladín", 130, 0.90, 1.8),
                new Ataque("Bárbaro", 160, 0.90, 2.1),
                new Ataque("Nigromante", 100, 0.95, 2.3),
        };

        for (Ataque ataque : ataques) {
            Future<Integer> futuro = pool.submit(new TareaCalcularDano(ataque));
            futuros.add(futuro);
        }

        int totalRaid = 0;
        for (Future<Integer> futuro : futuros) {
            try {
                int dano = futuro.get(); 
                totalRaid += dano;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Daño total de la raid: " + totalRaid);
        pool.shutdown();
    }
}