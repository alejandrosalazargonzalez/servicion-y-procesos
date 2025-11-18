package com.docencia.com.examen.procesos.services.interfaces;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public interface CommandService {
    boolean processLine(String command, boolean changeCmd);
}
