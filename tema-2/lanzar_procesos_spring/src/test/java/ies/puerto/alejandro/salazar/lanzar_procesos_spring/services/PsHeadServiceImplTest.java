package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.file.FileJobRepository;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.impl.PsHeadServiceImpl;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces.CommandService;

public class PsHeadServiceImplTest {
    
    CommandService commandService;
    @BeforeEach
    void beforeEach(){
        commandService = new PsHeadServiceImpl();
        commandService.setComando("ps");
        commandService.setFileRepository(new FileJobRepository());

    }

    @Test
    void validarTest(){
        String[] arrayCommand = {"ps","aux | head"};
        
        boolean validar = commandService.validar(arrayCommand);
        Assertions.assertTrue(validar, "error de validacion");
    }

    @Test
    void validarconMenosTest(){
        String[] arrayCommand = {"ps","-aux | head"};
        boolean validar = commandService.validar(arrayCommand);
        Assertions.assertTrue(validar, "error de validacion");
    }

    @Test
    void validarTestVacio(){
        String[] arrayComando = {"ps"};
        boolean valida = commandService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestFalse(){
        String[] arrayComando = {"ps","au |"};
        boolean valida = commandService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestFalse2(){
        String[] arrayComando = {"ps","-"};
        boolean valida =commandService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void procesarLineaTest(){
        String linea = "ps aux | head";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertTrue(procesado, "error al procesar linea");
    }

    @Test
    void procesarLineaMalTest(){
        String linea = "paso au / head";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertFalse(procesado, "error al procesar linea");
    }
    
    @Test
    void procesarLineaSoloTest(){
        String linea = "ps ";
        boolean procesado = commandService.procesarLinea(linea);
        Assertions.assertTrue(procesado, "error al procesar linea");
    }

}
