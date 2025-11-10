package es.ies.puerto.lanzar_hilos;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import es.ies.puerto.lanzar_hilos.Cazahorrocruxes;

public class CazahorrocruxesTest {
    @Test
    void CazahorrocruxesUnGanadorYUnSoloHallazgo() {
        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        Cazahorrocruxes sim = new Cazahorrocruxes();
        sim.main();

        String salida = salidaCapturada.toString();

        assertTrue(sim.getEncontrado().get(), "Debe haberse encontrado un Horrocrux");
        assertTrue(
            sim.getGanador().get().equals("Harry") ||
            sim.getGanador().get().equals("Hermione") ||
            sim.getGanador().get().equals("Ron"),
            "El ganador debe ser Harry, Hermione o Ron"
        );

        long ocurrencias = salida.split("encontró un Horrocrux", -1).length - 1;
        assertEquals(1, ocurrencias, "Debe haber exactamente un hallazgo en la salida");
    }
}
