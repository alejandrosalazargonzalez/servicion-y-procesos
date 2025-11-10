package es.ies.puerto.lanzar_hilos;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FuerzaThorHulkTest {

    @Test
    void FuerzaThorHulk_terminaPorTiempo_yDeclaraResultado() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        FuerzaThorHulk fuerza = new FuerzaThorHulk();
        fuerza.main();

        String salida = output.toString();
        assertTrue(salida.contains("¡Tiempo!"),"La salida debe indicar que el tiempo terminó.");

        boolean resultadoValido = salida.contains("gana") || salida.contains("Empate");
        assertTrue(resultadoValido,"La salida debe contener 'gana' o 'Empate'.");
        assertTrue(fuerza.isTiempoTerminado(), "El flag de tiempo debe ser verdadero.");
        assertTrue(fuerza.getTotalThor() >= 0 && fuerza.getTotalHulk() >= 0,"Los totales deben ser no negativos.");
    }
}