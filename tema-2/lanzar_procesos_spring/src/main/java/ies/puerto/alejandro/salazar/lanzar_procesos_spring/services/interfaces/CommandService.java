package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.interfaces.JobRepository;

public abstract class CommandService {
    
    JobRepository fileRepository;
    
    public JobRepository getFileRepository(){
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(JobRepository fileRepository){
        this.fileRepository = fileRepository;
    }
    
    private String comando;
    private Job tipo;
    private String expresionRegular;
    
    public Job getTipo() {
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
    
    public void setTipo(Job tipo) {
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
        String[] arrayComando = linea.split("\s+");
        setComando(arrayComando[0]);
        System.out.println("Comando: " + getComando());
        if (!validar(arrayComando)) {
            System.out.println("el comando no es valido");
            return;
        }
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea+" > "+ fileRepository.getPath())
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
        if (arrayComando.length - 1 == 0) {
            return true;
        }

        String parametro = arrayComando[arrayComando.length -1];
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
