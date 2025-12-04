package repasoParcial;

import java.util.Random;

class Ejercicio1 {
    public static void main(String[] args) {
        int N = 10;

        Edificio edificio = new Edificio(N);

        new Thread(new Ascensor(edificio), "Ascensor").start();

        for (Integer i = 0; i < 50; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Persona(i + 1, pisoRandom(), edificio), "Persona").start();

        }
    }

    private static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    private static int pisoRandom() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return 0;
        } else
            return 1;
    }

}