package es.ies.puerto.lanzar_hilos;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class FabricaDroidsTest {

    @Test
    void FabricaDroidsNoSeActivaAntesDeEnsamblaryCuentaCorrecta() {

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada));

        FabricaDroids fabrica = new FabricaDroids();
        fabrica.main();

        String salida = salidaCapturada.toString();

        for (int k = 1; k <= fabrica.getN(); k++) {
            String ensamblado = "Ensamblado Droid-" + k;
            String activado = "Activado Droid-" + k;

            int idxE = salida.indexOf(ensamblado);
            int idxA = salida.indexOf(activado);

            assertNotEquals(idxE,-1, "Debe haberse ensamblado " + ensamblado);
            assertNotEquals(idxA,-1, "Debe haberse activado " + activado);
            assertTrue(idxE < idxA, "El " + activado + " no puede ocurrir antes de " + ensamblado);
        }
        
        assertEquals(fabrica.getN(), fabrica.getActivados().get(),
        "Debe haberse activado exactamente N droids");
    }
}
