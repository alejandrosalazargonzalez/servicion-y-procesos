import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import es.ies.puerto.lanzar_hilos.BatallaPokemon;

public class BatallaPokemonTest {

    @Test
    public void BatallaPokemonDebeHaberGanador(){
 ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        BatallaPokemon juego = new BatallaPokemon();
   //     juego.main();

        String salida = salidaCapturada.toString();

        assertTrue(salida.contains("ha ganado la batalla!"),
                "Debe aparecer mensaje de victoria");
        assertTrue(juego.juegoTerminado.get(), "El juego debe haber terminado");
        assertTrue(juego.hpPikachu <= 0 || juego.hpCharmander <= 0,
                "Al menos uno debe tener HP <= 0");
    }
}
