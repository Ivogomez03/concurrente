package ejercicioGuiaMoni7;

import java.util.Random;

public class Ejercicio {

    public static void main(String[] args) {
        Juego juego = new Juego("palabraSecreta99");

        for (int i = 0; i < 100; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Jugador(juego, "palabraSecreta" + i, 300, i), "Apostador").start();

        }

    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
