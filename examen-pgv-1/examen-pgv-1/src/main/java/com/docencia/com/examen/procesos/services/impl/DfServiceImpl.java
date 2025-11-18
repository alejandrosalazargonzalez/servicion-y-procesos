package com.docencia.com.examen.procesos.services.impl;

import org.springframework.stereotype.Component;

import com.docencia.com.examen.procesos.domain.Job;
import com.docencia.com.examen.procesos.services.impl.abstracts.CommandServiceAbstract;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
@Component
public class DfServiceImpl extends CommandServiceAbstract {

    public DfServiceImpl() {
        this.setTipo(Job.DF);
        this.setExpresionRegular("^((-(h|H))|\\s*)$");
    }
}
