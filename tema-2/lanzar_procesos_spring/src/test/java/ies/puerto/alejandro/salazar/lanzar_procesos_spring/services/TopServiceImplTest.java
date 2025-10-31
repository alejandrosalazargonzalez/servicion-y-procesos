package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.file.FileJobRepository;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl.TopServiceImpl;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

public class TopServiceImplTest {
    
    CommandService commandService;
    @BeforeEach
    void beforeEach(){
        commandService = new TopServiceImpl();
        commandService.setComando("top");
        commandService.setFileRepository(new FileJobRepository());

    }

    @Test
    void validarTest(){
        String[] arrayCommand = {"top","-bn1"};
        boolean validar = commandService.validar(arrayCommand);
        Assertions.assertTrue(validar, "error de validacion");
    }

    @Test
    void validarTestMenos(){
        String[] arrayComando = {"top","-"};
        boolean valida = commandService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

        @Test
    void validarTestVacio(){
        String[] arrayComando = {"top"};
        boolean valida = commandService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }
    @Test
    void validarTestFalse(){
        String[] arrayComando = {"top","iiiiii"};
        boolean valida = commandService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestFalse2(){
        String[] arrayComando = {"top","-iiiiii"};
        boolean valida =commandService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void procesarLineaTest(){
        String linea = "top -bn1";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertTrue(procesado, "error al procesar linea");
    }

    @Test
    void procesarLineaMalTest(){
        String linea = "tapy -i";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertFalse(procesado, "error al procesar linea");
    }

    @Test
    void procesarLineaSoloTest(){
        String linea = "top ";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertTrue(procesado, "error al procesar linea");
    }

}
