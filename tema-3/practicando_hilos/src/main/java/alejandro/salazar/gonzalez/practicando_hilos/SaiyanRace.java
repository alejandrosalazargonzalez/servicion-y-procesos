package alejandro.salazar.gonzalez.practicando_hilos;

import java.util.Random;

public class SaiyanRace implements Runnable {

    private String name;
    private int distance = 0;
    private static final int GOAL = 100;
    private static volatile boolean winnerDeclared= false;

    /**
     * constructor con el nombre
     * @param name del personaje
     */
    public SaiyanRace(String name){
        this.name = name;
    }
    @Override
    public void run() {
        Random random = new Random();
        while (distance < GOAL && !winnerDeclared) {
            int step = random.nextInt(10) + 1;
            distance += step;
            System.out.println(name + " avanzo " + step + " metros");
        }

    }

    public static void main(String[] args) {
        
    }
}
