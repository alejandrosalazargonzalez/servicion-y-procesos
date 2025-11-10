package es.ies.puerto.lanzar_hilos;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class TardisTest {

    @Test
    void TardisExisteUnaEraGanadora() {
        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        Tardis tardis = new Tardis();
        tardis.main();

        String salida = salidaCapturada.toString();

        assertTrue(tardis.isDestinoAlcanzado(), "Debe haberse alcanzado un destino");
        assertNotNull(tardis.getEraGanadora(), "Debe existir una era ganadora");

        long ocurrencias = salida.split("llegó primero", -1).length - 1;
        assertEquals(1, ocurrencias, "Solo una era debe haber llegado primero");
    }
}