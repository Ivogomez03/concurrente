import java.util.Random;

public class EjercicioAutobus {
    public static void main(String[] args) {

        int N = 10;

        EstacionAutobus estacion = new EstacionAutobus(N);

        for (int i = 0; i < 30; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Pasajero(estacion, i + 1), "Pasajero").start();

        }

        new Thread(new Autobus(estacion), "Autobus").start();
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
