package ejercicioParcialAutobus;

/*
 * Un pasajero que llega a la estación de autobuses hace fila en el área de embarque. El autobús llega con
 * N asientos disponibles. El proceso de embarque se desarrolla de la siguiente manera: Cuando el autobús llega,
 * los pasajero que ya están en el área de embarque pueden subir siempre que haya asientos disponibles y que
 * haya pasajeros en la fila. Una vez que no quedan asientos libres o no hay más pasajeros en la fila, el autobús
 * se marcha.
 * 
 * Se consideran 2 enfoques alternativos:
 *  a) El conductor del autobús invita a todos los pasajeros en el área de embarque a subir, siempre que haya
 *     asientos disponibles y pasajeros en la fila. Si alguna de estas condiciones no se cumple, el conductor se
 *     marcha.
 * 
 *  b) El conductor invita al primer pasajero a subir si hay un asiento disponible y hay un pasajero en la fila.
 *     Luego, el último pasajero que subió invita al siguiente en la fila a embarcar, siempre que haya un asiento
 *     disponible y un pasajero esperando. Si alguna de estas condiciones no se cumple, el conductor se marcha o
 *     el último pasajero le pide que se retire.
 */
import java.util.Random;

public class Ejercicio {
    public static void main(String[] args) {
        Estacion estacion = new Estacion();
        Integer N = 30;

        for (Integer i = 0; i < N; i++) {

            try {
                Thread.sleep(tiempoRandom(0, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Pasajero(i + 1, estacion), "Pasajero").start();

        }

        new Thread(new Autobus(N, 1, estacion), "Autobus").start();
    }

    public static Integer tiempoRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

}
