package ejercicioGuiaMoni9;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Viaje {
    public Lock lock = new ReentrantLock();
    public Condition condicionCosta0;
    public Condition condicionCosta1;
    public Condition boteCruzar;
    public Condition personasEsperanCruce;

    private Integer personasEnElBote;
    private Demon demon;
    private Integer timeOut;
    private Integer capacidad;
    private Integer costaBote; // costa 0 o costa 1

    public Viaje(Integer n) {
        this.condicionCosta0 = lock.newCondition();
        this.condicionCosta1 = lock.newCondition();
        this.boteCruzar = lock.newCondition();
        this.personasEsperanCruce = lock.newCondition();

        this.capacidad = n;
        this.personasEnElBote = 0;
        this.costaBote = 0;
        this.timeOut = 2000;
        this.demon = new Demon(this);

        demon.start();
    }

    public void subir(Integer costa, Integer id) throws InterruptedException {
        lock.lock();
        try {
            if (costa == 0) {
                while (personasEnElBote >= capacidad || costaBote != costa)
                    condicionCosta0.await();
                personasEnElBote++;

                System.out.println("Persona subiendo al barco en la costa " + costa);

                if (personasEnElBote >= capacidad) {
                    boteCruzar.signal();

                }

                personasEsperanCruce.await();

                bajar(costa, id);
            } else {
                while (personasEnElBote >= capacidad || costaBote != costa)
                    condicionCosta1.await();
                personasEnElBote++;

                System.out.println("Persona subiendo al barco en la costa " + costa);

                if (personasEnElBote >= capacidad) {
                    boteCruzar.signal();

                }

                personasEsperanCruce.await();

                bajar(costa, id);

            }

        } finally {
            lock.unlock();
        }

    }

    public void cruzar() throws InterruptedException {
        lock.lock();
        try {
            boteCruzar.await();

        } finally {
            lock.unlock();
        }

        Thread.sleep(timeOut);
        lock.lock();
        try {
            if (costaBote == 0) {
                System.out.println("Barco cruzando de costa 0 a costa 1");
                costaBote = 1;

            } else {
                System.out.println("Barco cruzando de costa 1 a costa 0");
                costaBote = 0;

            }
            personasEsperanCruce.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void bajar(Integer costa, Integer id) throws InterruptedException {
        lock.lock();
        try {
            if (costa == 0) {

                personasEnElBote--;

                if (personasEnElBote == 0) {
                    condicionCosta1.signalAll();
                }
            } else {

                personasEnElBote--;

                if (personasEnElBote == 0) {
                    condicionCosta0.signalAll();
                }

            }

        } finally {
            lock.unlock();
        }

    }

    public Integer getTimeOut() {
        return timeOut;
    }

}
