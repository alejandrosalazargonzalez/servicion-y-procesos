package com.docencia.com.examen.procesos.repositories.interfaces;

import org.springframework.stereotype.Repository;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
@Repository
public interface JobRepository {
    boolean add(String content);
}
