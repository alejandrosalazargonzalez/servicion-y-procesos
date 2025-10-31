package ies.puerto.alejandro.salazar.lanzar_procesos_spring.services.interfaces;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import ies.puerto.alejandro.salazar.lanzar_procesos_spring.domain.Job;
import ies.puerto.alejandro.salazar.lanzar_procesos_spring.repositories.file.FileJobRepository;

/**
 *  @author: alejandrosalazargonzalez
 *  @version: 1.0.0
 */
public abstract class CommandService {
    
    FileJobRepository fileRepository;
    
    private String comando;
    private Job tipo;
    private String expresionRegular;

    public FileJobRepository getFileRepository(){
        return fileRepository;
    }

    @Autowired
    public void setFileRepository(FileJobRepository fileRepository){
        this.fileRepository = fileRepository;
    }
    
    public void setTipo(Job tipo) {
        this.tipo = tipo;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

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

    public String getComando() {
        return this.comando;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    /**
     * procesa la linea de comando introducida
     * @param linea de comando
     * @return true/false
     */
    public boolean procesarLinea(String linea){
        String[] arrayComando = linea.split("\s+");
        setComando(arrayComando[0]);
        System.out.println("Comando: " + getComando());
        if (!validar(arrayComando)) {
            System.out.println("el comando no es valido");
            return false;
        }
        Process proceso;
        try {
            proceso = new ProcessBuilder("sh", "-c", linea+" > "+ fileRepository.getPath())
            .start();
            ejecutarProceso(proceso);
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ejecuta el proceso introducido
     * @param proceso
     * @return true/false
     */
    public boolean ejecutarProceso(Process proceso){
        try{
            proceso.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * valida los parametros del comando
     * @param arrayComando
     * @return true/false
     */
    public boolean validar(String[] arrayComando) {
        if (!validarComando()) {
            return false;
        }
        
        if (arrayComando.length - 1 == 0) {
            return true;
        }
        
        String parametro = String.join(" ", Arrays.copyOfRange(arrayComando,1,arrayComando.length));
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = pattern.matcher(parametro);
        if (!matcher.find()) {
            System.out.println("No cumple");
            return false;
        }
        return true;
    }

    /**
     * comprueba que el comando sea valido
     * @return true/false
     */
    public boolean validarComando() {
        if (!getComando().toUpperCase().equals(getTipoString())) {
            System.out.println("el comando no es valido");
            return false;
        }
        return true;
    }
}
