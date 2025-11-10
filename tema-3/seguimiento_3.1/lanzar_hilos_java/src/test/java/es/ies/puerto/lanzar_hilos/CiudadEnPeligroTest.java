package es.ies.puerto.lanzar_hilos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import es.ies.puerto.lanzar_hilos.CiudadEnPeligro;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CiudadEnPeligroTest {

    @Test
    void CiudadEnPeligroSoloNeutralizaElOtroSeDetiene() {
        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        CiudadEnPeligro ciudad = new CiudadEnPeligro();
        ciudad.main();

        String salida = salidaCapturada.toString();

        assertTrue(ciudad.isAmenazaNeutralizada(), "Debe haberse neutralizado la amenaza");
        assertTrue(
            ciudad.getGanador().equals("Superman") || ciudad.getGanador().equals("Batman"),
            "El ganador debe ser Superman o Batman"
        );

        long ocurrencias = salida.split("Amenaza neutralizada", -1).length - 1;
        assertEquals(1, ocurrencias, "Solo debe imprimirse una neutralización");
    }
}