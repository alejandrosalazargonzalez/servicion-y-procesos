package org.formacion.procesos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComandoLsServicesTest {
    ComandoLsService comandoLsService;

    @BeforeEach
    void before(){
        comandoLsService = new ComandoLsService();
        comandoLsService.setComando("ls");
    }

    @Test
    void validarTest(){
        String[] arrayComando = {"ls","-la"};
        boolean valida = comandoLsService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestMenos(){
        String[] arrayComando = {"ls","-"};
        boolean valida = comandoLsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestVacio(){
        String[] arrayComando = {"ls"," "};
        boolean valida = comandoLsService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }
    @Test
    void validarTestFalse(){
        String[] arrayComando = {"ls","lalalalala"};
        boolean valida = comandoLsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestFalse2(){
        String[] arrayComando = {"ls","-lalalalala"};
        boolean valida = comandoLsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }
}
