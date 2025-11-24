
package es.ies.puerto.alejandro.salazar.seguimiento3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * reto 4: enums para constantes
 * 
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class GameUtils {

    public enum ClasePersonaje {
        MAGO, GUERRERO, PICARO, ARQUERO, PALADIN, NIGROMANTE
    }

    public enum Zona {
        BOSQUE_MALDITO, RUINAS_ANTIGUAS, PANTANO, CIUDAD_CIBERNETICA, TEMPLO
    }

    public enum TipoEnemigo {
        SLIME(1), ESQUELETO(2), DRAGON(5), LICH(4), BANDIDO(2);

        public final int dificultad;

        TipoEnemigo(int dificultad) {
            this.dificultad = dificultad;
        }
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void log(String hilo, String mensaje) {
        String time = LocalDateTime.now().format(formatter);
        System.out.printf("[%s] [%s] %s%n", time, hilo, mensaje);
    }
}