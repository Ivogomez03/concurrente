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

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EstacionAutobus {
    private Lock lock;

    private Condition noPuedoSubir;
    private Condition noMePuedoMarchar;

    private int capacidad;
    private int asientosDisponibles;
    private int pasajerosEnLaFila;

    public EstacionAutobus(int capacidad) {
        this.lock = new ReentrantLock();
        this.noMePuedoMarchar = lock.newCondition();
        this.noPuedoSubir = lock.newCondition();
        this.capacidad = capacidad;
        this.asientosDisponibles = capacidad;
        this.pasajerosEnLaFila = 0;

    }

    public void subirAlAutobus(int id) {
        lock.lock();
        try {
            pasajerosEnLaFila++;
            while (asientosDisponibles == 0) {

                System.out.println("Pasajero " + id + " espera en la fila.");
                noPuedoSubir.await();
            }
            pasajerosEnLaFila--;

            asientosDisponibles--;
            System.out.println("Pasajero " + id + " se subio al autobus");

            if (asientosDisponibles == 0 || pasajerosEnLaFila == 0) {
                noMePuedoMarchar.signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void autobusLlega() {
        lock.lock();
        try {
            System.out.println("Autobus llega al area de embarque");
            while (asientosDisponibles > 0 && pasajerosEnLaFila > 0) {
                noPuedoSubir.signalAll();
                noMePuedoMarchar.await();
            }

            System.out.println("Autobus marchandose");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
