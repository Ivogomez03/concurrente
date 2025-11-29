package ejercicioGuiaMoni5;

import java.util.Random;

public class Ejercicio {
    public static void main(String[] args) {
        Peluqueria peluqueria = new Peluqueria();

        for (int i = 0; i < 4; i++) {
            new Thread(new Peluquero(peluqueria, i), "Peluquero").start();
        }

        for (int i = 0; i < 40; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Cliente(peluqueria, peluqueroRandom()), "Cliente").start();

        }
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    public static int peluqueroRandom() {
        Random random = new Random();
        if (random.nextDouble() < 0.25) {
            return 0;
        } else if (random.nextDouble() < 0.5) {
            return 1;
        } else if (random.nextDouble() < 0.75) {
            return 2;
        }
        return 3;
    }
}