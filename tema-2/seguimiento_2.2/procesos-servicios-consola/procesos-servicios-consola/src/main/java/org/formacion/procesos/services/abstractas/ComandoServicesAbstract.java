package org.formacion.procesos.services.abstractas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.formacion.procesos.domain.ProcessType;
import org.formacion.procesos.repositories.interfaces.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComandoServicesAbstract {
    
    CrudInterface fileRepository;
    
    public CrudInterface getFileRepository(){
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(CrudInterface fileRepository){
        this.fileRepository = fileRepository;
    }
    
    private String comando;
    private ProcessType tipo;
    private String expresionRegular;
    
    public ProcessType getTipo() {
        if (this.tipo == null) {
            return null;
        }
        return this.tipo;
    }

    public String getTipoString() {
        if (this.tipo == null) {
            return null;
        }
        return this.tipo.toString();
    }
    
    public void setTipo(ProcessType tipo) {
        this.tipo = tipo;
    }
    

    public String getComando() {
        return this.comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public void procesarLinea(String linea){
        String[] arrayComando = linea.split("\s*");
        setComando(arrayComando[0]);
        System.out.println("Comando: " + getComando());
        if (!validar(arrayComando)) {
            System.out.println("el comando no es valido");
            return;
        }
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea+" > mis_procesos.txt")
            .start();
            proceso.waitFor();
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
    
    public boolean ejecutarProceso(Process proceso){
        try{
            proceso.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean validar(String[] arrayComando) {
        if (!validarComando()) {
            return false;
        }
        String parametro = arrayComando[1];
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            System.out.println("No cumple");
            return false;
        }
        return true;
    }

    public boolean validarComando() {
    if (!getComando().toUpperCase().equals(getTipoString())) {
        System.out.println("el comando no es valido");
        return false;
    }
    return true;
    }
}
