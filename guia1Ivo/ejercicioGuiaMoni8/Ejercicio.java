package ejercicioGuiaMoni8;

import java.util.Random;

public class Ejercicio {

    public static void main(String[] args) {
        Bar bar = new Bar();

        new Thread(new MaestroPizzero(bar), "Maestro pizzero").start();

        for (int i = 0; i < 100; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Cliente(bar), "Cliente").start();

        }

    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
