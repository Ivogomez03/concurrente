package ejercicioParcialAutobus;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Estacion {
    private Lock lock;
    private Condition condition;
    private Condition autobusEsperando;

    private Integer asientosDisponibles;
    private Integer pasajerosEnLaFila;

    private Boolean autobusPresente;

    public Estacion() {
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.autobusEsperando = lock.newCondition();
        this.pasajerosEnLaFila = 0;
        this.autobusPresente = false;

    }

    public void solicitarSubirse(Integer id) {
        lock.lock();
        try {

            pasajerosEnLaFila++;
            System.out.println("El pasajero " + id + " se encolo en la fila");

            while (!autobusPresente || asientosDisponibles == 0) {
                condition.await();
            }

            pasajerosEnLaFila--;
            asientosDisponibles--;

            System.out.println("El pasajero " + id + " se subio al bus");

            autobusEsperando.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void autobusLlega(Integer id, Integer capacidad) {
        lock.lock();
        try {
            System.out.println("El autobus con id " + id + " ha llegado a la estación.");
            autobusPresente = true;
            asientosDisponibles = capacidad;

            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void autobusSeVa(Integer id, Integer capacidad) {
        lock.lock();
        try {

            while (asientosDisponibles > 0 && pasajerosEnLaFila > 0) {
                autobusEsperando.await();
            }
            System.out.println("El autobus con id " + id + " se ha marchado de la estación.");
            autobusPresente = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
