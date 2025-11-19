package ejercicioGuiaMoni9;

import java.util.Random;

class Ejercicio {
    public static void main(String[] args) {
        int N = 10;

        Viaje viaje = new Viaje(N);

        for (Integer i = 0; i < 80; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Persona(i + 1, costaRandom(), viaje), "Persona").start();

        }
    }

    private static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    private static Integer costaRandom() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return 0;
        } else
            return 1;
    }

}