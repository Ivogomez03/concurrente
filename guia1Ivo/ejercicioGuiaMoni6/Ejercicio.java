package ejercicioGuiaMoni6;

import java.util.Random;

public class Ejercicio {

    public static void main(String[] args) {
        Auditorio auditorio = new Auditorio();

        new Thread(new Orador(auditorio), "Orador").start();

        for (int i = 0; i < 200; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Oyentes(auditorio), "Oyente").start();

        }

    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
