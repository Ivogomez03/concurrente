package ejercicioGuiaMoni8;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
8. En el bar X se fabrican dos tipos de pizzas: las chicas y las grandes. El maestro pizzero va colocando
de a una las pizzas listas en una barra para que los clientes se sirvan y pasen luego por la caja.
Lamentablemente todos los clientes tienen buen apetito y se comportan de la siguiente manera: prefieren
siempre tomar un pizza grande, pero si no lo logran se conforman con dos pequeñas. Los clientes son
mal educados y no esperan en una cola (todos compiten por las pizzas). Dar una solucion en donde
cada cliente es modelado como un thread. Como mecanismo de sincronizacion, utilizar monitores
 */

public class Bar {
    private Lock lock;
    private Condition noHayPizzas;

    private int cantidadDePizzasPequeñasEnBarra;
    private int cantidadDePizzasGrandesEnBarra;

    public Bar() {
        this.lock = new ReentrantLock();
        this.noHayPizzas = lock.newCondition();
        this.cantidadDePizzasGrandesEnBarra = 0;
        this.cantidadDePizzasPequeñasEnBarra = 0;
    }

    public void agarrarPizzas() {
        lock.lock();
        try {
            while (cantidadDePizzasGrandesEnBarra == 0 && cantidadDePizzasPequeñasEnBarra < 2) {
                noHayPizzas.await();
            }

            if (cantidadDePizzasGrandesEnBarra > 0) {
                System.out.println("Tomando pizza grande");
                cantidadDePizzasGrandesEnBarra--;
            } else {
                System.out.println("Tomando 2 pizzas pequeñas");
                cantidadDePizzasPequeñasEnBarra -= 2;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void servirPizzas() {
        lock.lock();
        try {
            Random random = new Random();

            if (random.nextDouble() < 0.5) {
                cantidadDePizzasGrandesEnBarra++;
            } else {
                cantidadDePizzasPequeñasEnBarra++;
            }

            if (cantidadDePizzasGrandesEnBarra + cantidadDePizzasPequeñasEnBarra > 0) {
                noHayPizzas.signalAll();
            }

        } finally {
            lock.unlock();
        }
    }

}
