package ejercicioParcial1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Puente {
    private Condition[] sentidos;
    private Integer cochesCruzandoAaB;
    private Integer cochesCruzandoBaA;
    private Lock lock;

    public Puente() {
        this.lock = new ReentrantLock();
        this.sentidos = new Condition[2];
        this.sentidos[0] = lock.newCondition();
        this.sentidos[1] = lock.newCondition();

        this.cochesCruzandoAaB = 0;
        this.cochesCruzandoBaA = 0;

    }

    public void solicitarPaso(Integer sentido, Integer id) {
        lock.lock();
        try {
            if (sentido == 1) {

                while (cochesCruzandoAaB > 0) {
                    sentidos[1].await();
                }

                cochesCruzandoBaA++;

                System.out.println("El coche " + id + " esta cruzando desde B a A");

            } else {
                while (cochesCruzandoBaA > 0) {
                    sentidos[0].await();
                }

                cochesCruzandoAaB++;

                System.out.println("El coche " + id + " esta cruzando desde A a B");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void termineDeCruzar(Integer sentido, Integer id) {
        lock.lock();
        try {
            if (sentido == 1) {

                cochesCruzandoBaA--;

                System.out.println("El coche " + id + " termino de cruzar desde B a A");

                if (cochesCruzandoBaA == 0) {
                    sentidos[0].signalAll();
                } else {
                    sentidos[1].signal();
                }

            } else {
                cochesCruzandoAaB--;

                System.out.println("El coche " + id + " termino de cruzar desde A a B");

                if (cochesCruzandoAaB == 0) {
                    sentidos[1].signalAll();
                } else {
                    sentidos[0].signal();
                }

            }
        } finally {
            lock.unlock();
        }
    }

}
