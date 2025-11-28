import java.util.Random;

public class Ejercicio {

    public static void main(String[] args) {
        Planta planta = new Planta();

        new Thread(new Administracion(planta), "Administracion").start();

        for (int i = 0; i < 3; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Estacion(planta, i), "Estacion").start();

        }

        for (Integer i = 0; i < 20; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Vehiculo(tipoRandom(), i + 1, planta), "Vehiculo").start();

        }
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    public static int tipoRandom() {
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            return 0;
        } else {
            return 1;
        }
    }

}
