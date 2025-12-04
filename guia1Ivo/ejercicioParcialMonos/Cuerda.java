package ejercicioParcialMonos;
/*
 * Algunos monos estan intentando cruzar un barranco. Hay una sola cuerda que atraviesa el barranco, y los monos
 * pueden cruzar de forma horizontal. Hasta cinco monos pueden estar colgados de la cuerda al mismo tiempo; si
 * hay mas de cinco, la cuerda se rompera y todos moriran. Además, si los monos que se mueven hacie el este se
 * encuentran con monos que cruzar hacia el oeste, todos caeran y moriran. Cada mono opera en un hilo separado.
 * 
 * a) un máximo de cinco monos pueden ejecutar crossRavine() al mismo tiempo
 * b) Todos los monos que están ejecutando crossRavine() se dirijen en la misma dirección
 * c) Ningún mono debe esperar innecesariamente. Es decir, si hay monos cruzando en una direccion y llega otro
 * que quiere cruzar en esa dirección lo puede hacer, a pesar de que hay monos esperando al otro lado.
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cuerda {
    private Lock lock;
    private int capacidadCuerda;
    private Condition[] destinos;
    private int[] monosEnCuerda;

    public Cuerda() {
        this.lock = new ReentrantLock(true);
        this.capacidadCuerda = 5;
        this.destinos = new Condition[2];
        this.destinos[0] = lock.newCondition();
        this.destinos[1] = lock.newCondition();
        this.monosEnCuerda = new int[2];
        this.monosEnCuerda[0] = 0;
        this.monosEnCuerda[1] = 0;
    }

    public void cruzarCuerda(int destino, int id) {
        lock.lock();
        try {

            while (monosEnCuerda[1 - destino] > 0 || monosEnCuerda[destino] == capacidadCuerda) { // si hay monos
                                                                                                  // cruzando desde el
                                                                                                  // otro lado, o hay 5
                                                                                                  // de mi lado
                System.out.println("Mono " + id + " con destino " + destino + " espera en el origen " + (1 - destino));
                destinos[destino].await();

            }
            monosEnCuerda[destino]++;

            System.out.println("Mono " + id + " cruzando cuerda con destino a " + destino);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void llegarAdestino(int destino, int id) {
        lock.lock();
        try {
            System.out.println("Mono " + id + " llego a destino " + destino);

            monosEnCuerda[destino]--;

            if (monosEnCuerda[destino] == 0) {
                destinos[1 - destino].signalAll();
            } else {
                destinos[destino].signal();
            }

        } finally {
            lock.unlock();
        }

    }
}