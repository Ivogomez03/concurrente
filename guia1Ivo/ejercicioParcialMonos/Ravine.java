package ejercicioParcialMonos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ravine {
    private Integer capacidad;
    private Condition[] destinos; // destinos[0] para oeste a este, destinos[1] para este a oeste
    private Lock lock;
    private Integer cantMonosOesteEste;
    private Integer cantMonosEsteOeste;

    public Ravine() {
        this.capacidad = 5;
        this.lock = new ReentrantLock();
        this.destinos = new Condition[2];
        this.destinos[0] = lock.newCondition();
        this.destinos[1] = lock.newCondition();
        this.cantMonosEsteOeste = 0;
        this.cantMonosOesteEste = 0;
    }

    public void WaitUntilSafeToCross(String destino, Integer id) {
        lock.lock();
        try {
            if (destino.equals("Este")) {
                while (cantMonosEsteOeste > 0 || cantMonosOesteEste >= capacidad) {
                    destinos[0].await();
                }

                cantMonosOesteEste++;

            } else {
                while (cantMonosOesteEste > 0 || cantMonosEsteOeste >= capacidad) {
                    destinos[1].await();
                }

                cantMonosEsteOeste++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void CrossRavine(Integer id, String destino) {
        if (destino.equals("Este")) {
            System.out.println("El mono " + id + " esta cruzando de Oeste a Este");
        } else {
            System.out.println("El mono " + id + " esta cruzando de Este a Oeste");
        }

    }

    public void DoneWithCrossing(String destino, Integer id) {
        lock.lock();
        try {
            if (destino.equals("Este")) {

                cantMonosOesteEste--;

                System.out.println("El mono " + id + " termino de cruzar la cuerda de Oeste a Este");

                if (cantMonosOesteEste == 0) {
                    destinos[1].signalAll();
                } else {
                    destinos[0].signal();
                }

            } else {

                cantMonosEsteOeste--;

                System.out.println("El mono " + id + " termino de cruzar la cuerda de Este a Oeste");

                if (cantMonosEsteOeste == 0) {
                    destinos[0].signalAll();
                } else {
                    destinos[1].signal();
                }

            }
        } finally {
            lock.unlock();
        }
    }

}
