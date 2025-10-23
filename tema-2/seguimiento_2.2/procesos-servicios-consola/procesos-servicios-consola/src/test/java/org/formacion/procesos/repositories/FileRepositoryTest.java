package org.formacion.procesos.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileRepositoryTest {

    static FileRepository fileRepository;

    @BeforeAll
    static void beforeAll(){
        fileRepository = new FileRepository();
        fileRepository.setFileName("mis_procesos.txt");
    }

    @Test
    void addContenidoTest(){
        boolean resultado = fileRepository.add("texto");
        Assertions.assertTrue(resultado,"no se ha optenido el resultado esperado");
    }
}
