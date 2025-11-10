package es.ies.puerto.lanzar_hilos;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class MilleniumFalconTest {

    @Test
    void MilleniumFalcon_finalizaConEscapeODestruccion() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        MilleniumFalcon mileniumFalcon = new MilleniumFalcon();
        mileniumFalcon.main();

        String salida = output.toString();

        boolean destruida = salida.contains("se destruye");
        boolean escapan = salida.contains("escapan");

        assertTrue(destruida ^ escapan, "La salida debe indicar destrucción o escape, pero no ambos. Salida:\n" + salida);
    }
}