package es.ies.puerto.lanzar_hilos;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import es.ies.puerto.lanzar_hilos.ExploradoresJedi;

class ExploradoresJediTest {

    @Test
    void ExploradoresJediUnSoloGanador() {
        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        ExploradoresJedi exploradores = new ExploradoresJedi();
        exploradores.main();

        String salida = salidaCapturada.toString();

        assertTrue(exploradores.isPistaEncontrada(), "Debe haberse encontrado una pista");
        long ocurrencias = salida.split("hallo una pista", -1).length;
        assertEquals(1, ocurrencias, "Debe haber exactamente un hallazgo en la salida");
    }
}