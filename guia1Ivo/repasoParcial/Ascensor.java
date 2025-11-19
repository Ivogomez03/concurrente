package repasoParcial;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ascensor implements Runnable {

    private Lock lock;
    private Condition condicionPlantaBaja;
    private Condition condicionPrimerPiso;
    private Condition condicionPersonaSubio;
    private Integer cantidadActual;
    private Integer capacidad;
    private String pisoActual;
    private Integer timeOut;
    private Demonio demon;

    public Ascensor(Integer capacidad, String pisoActual) {
        this.lock = new ReentrantLock();
        condicionPlantaBaja = lock.newCondition();
        condicionPrimerPiso = lock.newCondition();
        condicionPersonaSubio = lock.newCondition();
        cantidadActual = 0;
        this.capacidad = capacidad;
        this.pisoActual = pisoActual;
        this.timeOut = 2000;
        this.demon = new Demonio(this);
        demon.start();
    }

    public void subirse(String pisoDeLaPersona, String nombrePersona) {
        lock.lock();

        try {
            while (pisoDeLaPersona != pisoActual || cantidadActual > capacidad) {
                if (pisoDeLaPersona == "Primer Piso") {
                    condicionPrimerPiso.await();
                } else {
                    condicionPlantaBaja.await();
                }
            }
            cantidadActual++;
            System.out.println("Se sube " + nombrePersona + " en el piso " + pisoActual);

            if (cantidadActual == capacidad)
                demon.interrupt();

            condicionPersonaSubio.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void bajarse(String nombrePersona) {
        lock.lock();

        try {
            cantidadActual--;
            System.out.println("Se baja " + nombrePersona + " en el piso " + pisoActual);
            if (pisoActual == "Planta Baja") {
                condicionPlantaBaja.signal();
            } else {
                condicionPrimerPiso.signal();
            }
        } finally {
            lock.unlock();
        }

    }

    public void moverse() {
        lock.lock();
        try {
            if (pisoActual == "Planta Baja")
                pisoActual = "Primer Piso";
            else
                pisoActual = "Planta Baja";

            System.out.println("El ascensor se ha movido al piso " + pisoActual);
            condicionPersonaSubio.signalAll();

        } finally {
            lock.unlock();
        }

    }

    public Integer getTimeOut() {
        return timeOut;
    }

    @Override
    public void run() {

    }
}