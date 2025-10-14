package org.formacion.procesos.controller.abstractas;

import java.util.List;

import org.formacion.procesos.domain.ProcessType;

public abstract class ComandoControllerAbstract {
    String comando;
    List<String> parametros;
    ProcessType tipo;
    
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

    public List<String> getParametros() {
        return this.parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    public void procesarLinea(String linea){
        String[] arrayComando = linea.split(" ");
        setComando(arrayComando[0]);
        System.out.println("Comando: " + getComando());
        if (!getComando().toUpperCase().equals(getTipoString())) {
            System.out.println("el comando no es valido");
        }
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea+" > mis_procesos.txt")
            .start();
            proceso.waitFor();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        imprimeMensaje();
    }
    
    public boolean ejecutarProceso(Process proceso){
        try{
            proceso.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public abstract void imprimeMensaje();
}
