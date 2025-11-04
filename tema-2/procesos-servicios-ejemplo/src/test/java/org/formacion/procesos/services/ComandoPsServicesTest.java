package org.formacion.procesos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComandoPsServicesTest {
    ComandoPsService comandoPsService;

    @BeforeEach
    void before(){
        comandoPsService = new ComandoPsService();
        comandoPsService.setComando("ps");
    }

    @Test
    void validarTest(){
        String[] arrayComando = {"ps","-xa"};
        boolean valida = comandoPsService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestMenos(){
        String[] arrayComando = {"ps","-"};
        boolean valida = comandoPsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestVacio(){
        String[] arrayComando = {"ps"," "};
        boolean valida = comandoPsService.validar(arrayComando);
        Assertions.assertTrue(valida,"se ha producido un error en la validacion");
    }
    @Test
    void validarTestFalse(){
        String[] arrayComando = {"ps","lalalalala"};
        boolean valida = comandoPsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }

    @Test
    void validarTestFalse2(){
        String[] arrayComando = {"ps","-lalalalala"};
        boolean valida = comandoPsService.validar(arrayComando);
        Assertions.assertFalse(valida,"se ha producido un error en la validacion");
    }
}
