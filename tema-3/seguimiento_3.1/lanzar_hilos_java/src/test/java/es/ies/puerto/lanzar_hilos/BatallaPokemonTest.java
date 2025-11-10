package es.ies.puerto.lanzar_hilos;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import es.ies.puerto.lanzar_hilos.BatallaPokemon;

class BatallaPokemonTest {

        @Test
        void BatallaPokemonDebeHaberGanador() {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                System.setOut(new PrintStream(out));

                BatallaPokemon juego = new BatallaPokemon();
                juego.main();

                String salida = out.toString();

                assertTrue(salida.contains("ha ganado la batalla!"));
                assertTrue(juego.getJuegoTerminado().get());
                assertTrue(juego.getHppikachu().get() <= 0 || juego.getHpcharmander().get() <= 0);
        }
}
