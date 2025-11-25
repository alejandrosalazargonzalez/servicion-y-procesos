package com.docencia.rest.exeption;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(){
        super();
    }

    public  ResourceNotFoundException(String mensaje){
        super(mensaje);
    }

    public ResourceNotFoundException(String mensaje, Throwable exeption){
        super(mensaje,exeption);
    }
}
